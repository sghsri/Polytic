package io.github.sghsri.representativeapp;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Scanner;

public class Scrapper {

    public static void main(String[] args) {
        getCandidateInfo("TX", 2, -95.56698879999999, 29.7934907);
    }

    public static CandidateInfo getCandidateInfo(String stateAbbrev, int congDistr, double lat, double lon) {
        CandidateInfo c = null;

        String url = "https://www.govtrack.us/congress/members/TX/7#q=&marker_lng=-95.64837279999999&marker_lat=29.908547300000002";
        Scanner s = new Scanner(getWebPage(url));
        while (s.hasNext()) {
            if (s.nextLine().contains("<div class=\"col-sm-3\" style=\"padding-right: 0; padding-bottom: 1em;\">")) {
                for (int i = 0; i < 7; i++) s.nextLine();
                String pageUrl = s.nextLine();
                pageUrl = pageUrl.substring(pageUrl.indexOf('\"') + 1);
                pageUrl = pageUrl.substring(0, pageUrl.indexOf('\"'));

                String name = s.nextLine().replaceAll("\\s\\s+", "");
                c = new CandidateInfo(name);
                scrapeSpecific(pageUrl, c);
            }
        }
        return c;
    }

    private static void scrapeSpecific(String extra, CandidateInfo c) {
        Scanner s = new Scanner(getWebPage("https://www.govtrack.us" + extra));
        while (s.hasNext()) {
            processWebpage(s.nextLine(), s, c);
        }
    }

    private static void processWebpage(String line, Scanner s, CandidateInfo c) {
        if (line.contains("Committee Membership")) {
            for (int i = 0; i < 6; i++) s.nextLine();
            boolean hasMoreCommittees = true;
            while (hasMoreCommittees) {
                String committeeName = s.nextLine().replaceAll("<a href=\".+\">", "").replaceAll("</a>", "");
                committeeName = committeeName.replaceAll("\\s\\s+", "");
                c.addCommittee(committeeName);
                for (int i = 0; i < 3; i++) s.nextLine();

                String str = s.nextLine();
                if (str.contains("<li style=\"margin: .125em 0\">")) {
                    s.nextLine();
                    continue;
                }
                while (!str.contains("</ul>")) {
                    String position = str.substring(str.indexOf("<li>") + 4);
                    position = position.substring(0, position.indexOf(", Subcommittee on"));
                    String subCom = str.substring(str.indexOf("<a href")).replaceAll("<[^>]+>", "");
                    c.findCommittee(committeeName).addCommittee(subCom, position);
                    str = s.nextLine().replaceAll("\\s\\s+", "");
                    if (str.isEmpty()) str = s.nextLine();
                }
                for (int i = 0; i < 5; i++) {
                    if (s.nextLine().contains("<!-- /membership -->")) {
                        hasMoreCommittees = false;
                        break;
                    }
                }
            }
        }
        if (line.contains("Enacted Legislation")) {
            for (int i = 0; i < 5; i++) s.nextLine();
            String str = s.nextLine();
            while (str.contains("<li style=\"margin-bottom: .3em\"><a href=")) {
                str = str.replaceAll("<[^>]+>", "").replaceAll("\\s\\s+", "");
                c.addEnactedLegislation(str);
                s.nextLine();
                str = s.nextLine();
            }
        }
        if (line.contains("Bills Sponsored")) {
            for (int i = 0; i < 6; i++) s.nextLine();
            String str = s.nextLine();
            while (str.contains("<span style=\"margin-right: 2em; display: inline-block;\">")) {
                str = str.replaceAll("<[^>]+>", "").replaceAll("\\s\\s+", "");
                String issue = str.substring(0, str.indexOf("(") - 1);
                double percent = Double.parseDouble(str.substring(str.indexOf("(") + 1, str.indexOf("%)")));
                c.addIssue(issue, percent);
                s.nextLine();
                str = s.nextLine();
            }
        }
    }

    private static String getWebPage(String webUrl) {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(webUrl).newBuilder();
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
package io.github.sghsri.representativeapp;
import java.io.Serializable;
import java.util.*;

public class CandidateInfo implements Serializable{
    private String mName;
    private Set<Committee> mCommittees = new HashSet<>();
    private List<String> mEnactedLegislation = new ArrayList<>();
    private Map<String, Double> mIssues = new HashMap<>();

    public CandidateInfo(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public Set<Committee> getCommittees() {
        return mCommittees;
    }

    public Committee findCommittee(String name) {
        for (Committee c : mCommittees) {
            if (c.getName().equals(name)) return c;
        }
        return null;
    }

    public List<String> getEnactedLegislation() {
        return mEnactedLegislation;
    }

    public void addEnactedLegislation(String legislation) {
        mEnactedLegislation.add(legislation);
    }

    public void addCommittee(String name) {
        mCommittees.add(new Committee(name));
    }

    public Map<String, Double> getIssues() {
        return mIssues;
    }

    public void addIssue(String s, double d) {
        mIssues.put(s, d);
    }
}

class Committee {
    private String mName;
    private Map<String, String> mSubcommittees = new HashMap<>();

    Committee(String name) {
        mName = name;
    }

    public void addCommittee(String subCom, String position) {
        mSubcommittees.put(subCom, position);
    }

    public String getName() {
        return mName;
    }

    public Map<String, String> getSubcommitees() {
        return mSubcommittees;
    }
}

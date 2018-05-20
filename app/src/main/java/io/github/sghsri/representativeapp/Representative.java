package io.github.sghsri.representativeapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Representative implements Serializable {

    private String name;
    private String office;
    private String party;
    private String phone;
    private String url;
    private String photoUrl;
    private String address;
    private ArrayList<SocialMedia> socialMedia;

    //committee membership,, enacted legislation, bills sponsored
    public Representative(String n, String o, String p, String ph, String u, String pu, String ad, ArrayList<SocialMedia> sm){
        name = n;
        office = o;
        party = p;
        phone = ph;
        url = u;
        photoUrl = pu;
        address = ad;
        socialMedia = sm;
    }

    public ArrayList<SocialMedia> getSocialMedia() {
        return socialMedia;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getOffice() {
        return office;
    }

    public String getParty() {
        return party;
    }

    public String getPhone() {
        return phone;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setSocialMedia(ArrayList<SocialMedia> socialMedia) {
        this.socialMedia = socialMedia;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class SocialMedia{
        String type;
        String id;
        public SocialMedia(String t, String i){
            type = t.toLowerCase();
            id = i;
        }

        public String getId() {
            return id;
        }

        public String getType() {
            return type;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type+" "+id;
        }
    }
}

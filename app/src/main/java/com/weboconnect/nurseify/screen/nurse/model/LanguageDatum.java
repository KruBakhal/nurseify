package com.weboconnect.nurseify.screen.nurse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LanguageDatum {
    public LanguageDatum(String language) {
        this.language = language;
    }

    @SerializedName("language")
@Expose
private String language;

public String getLanguage() {
return language;
}

public void setLanguage(String language) {
this.language = language;
}

}
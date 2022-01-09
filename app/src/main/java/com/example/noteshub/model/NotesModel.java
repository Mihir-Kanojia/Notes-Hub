package com.example.noteshub.model;

import com.google.firebase.firestore.ServerTimestamp;
import com.google.gson.annotations.SerializedName;
import com.google.firebase.firestore.DocumentId;

import java.util.Date;

public class NotesModel {

    @DocumentId
    public String id;

    @SerializedName("heading")
    public String heading;

    @SerializedName("description")
    public String description;

    @SerializedName("selectedTag")
    public String selectedTag;

    @ServerTimestamp
    public Date createdDate;

    @ServerTimestamp
    public Date lastUpdatedDate;

    public NotesModel() {
    }

    public NotesModel(String heading, String description,String selectedTag, Date lastUpdatedDate) {
        this.heading = heading;
        this.description = description;
        this.selectedTag = selectedTag;
        this.lastUpdatedDate = lastUpdatedDate;
        this.createdDate = new Date();
    }


}


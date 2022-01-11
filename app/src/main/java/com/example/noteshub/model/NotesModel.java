package com.example.noteshub.model;

import com.google.firebase.firestore.ServerTimestamp;
import com.google.gson.annotations.SerializedName;
import com.google.firebase.firestore.DocumentId;

import java.io.Serializable;
import java.util.Date;

public class NotesModel implements Serializable {

    @DocumentId
    public String id;

    @SerializedName("heading")
    public String heading;

    @SerializedName("description")
    public String description;

    @SerializedName("selectedTag")
    public String selectedTag;

    @SerializedName("isDeleted")
    public boolean isDeleted;

    @ServerTimestamp
    public Date createdDate;

    @ServerTimestamp
    public Date lastUpdatedDate;


    public NotesModel() {
    }

    public NotesModel(String heading, String description, String selectedTag,boolean isDeleted, Date lastUpdatedDate) {
        this.heading = heading;
        this.description = description;
        this.selectedTag = selectedTag;
        this.isDeleted = isDeleted;
        this.lastUpdatedDate = lastUpdatedDate;
        this.createdDate = new Date();
    }


}


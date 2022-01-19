package com.weboconnect.nurseify.screen.facility.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FacilitySettingModel {

    @SerializedName("api_status")
    @Expose
    private String apiStatus;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private FacilitySetting_Data data;

    public String getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(String apiStatus) {
        this.apiStatus = apiStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FacilitySetting_Data getData() {
        return data;
    }

    public void setData(FacilitySetting_Data data) {
        this.data = data;
    }

    public class FacilitySetting_Data {

        @SerializedName("facility_name")
        @Expose
        private String facilityName;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("postcode")
        @Expose
        private String postcode;
        @SerializedName("rating")
        @Expose
        private Rating rating;
        @SerializedName("review")
        @Expose
        private String review;
        @SerializedName("followers")
        @Expose
        private String followers;

        public String getFacilityName() {
            return facilityName;
        }

        public void setFacilityName(String facilityName) {
            this.facilityName = facilityName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public Rating getRating() {
            return rating;
        }

        public void setRating(Rating rating) {
            this.rating = rating;
        }

        public String getReview() {
            return review;
        }

        public void setReview(String review) {
            this.review = review;
        }

        public String getFollowers() {
            return followers;
        }

        public void setFollowers(String followers) {
            this.followers = followers;
        }

        public class Rating {

            @SerializedName("over_all")
            @Expose
            private String overAll;
            @SerializedName("on_board")
            @Expose
            private String onBoard;
            @SerializedName("nurse_team_work")
            @Expose
            private String nurseTeamWork;
            @SerializedName("leadership_support")
            @Expose
            private String leadershipSupport;
            @SerializedName("tools_todo_my_job")
            @Expose
            private String toolsTodoMyJob;

            public String getOverAll() {
                return overAll;
            }

            public void setOverAll(String overAll) {
                this.overAll = overAll;
            }

            public String getOnBoard() {
                return onBoard;
            }

            public void setOnBoard(String onBoard) {
                this.onBoard = onBoard;
            }

            public String getNurseTeamWork() {
                return nurseTeamWork;
            }

            public void setNurseTeamWork(String nurseTeamWork) {
                this.nurseTeamWork = nurseTeamWork;
            }

            public String getLeadershipSupport() {
                return leadershipSupport;
            }

            public void setLeadershipSupport(String leadershipSupport) {
                this.leadershipSupport = leadershipSupport;
            }

            public String getToolsTodoMyJob() {
                return toolsTodoMyJob;
            }

            public void setToolsTodoMyJob(String toolsTodoMyJob) {
                this.toolsTodoMyJob = toolsTodoMyJob;
            }

        }
    }
}
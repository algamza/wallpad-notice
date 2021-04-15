package com.wallpad.notice.repository.remote.entities;

import java.util.List;

public class RemoteVoteEntity {
    private int Error_Cd;
    private String Error_Nm;
    private int Total_Count;
    private Resource Resource;

    public RemoteVoteEntity(int error_Cd, String error_Nm, int total_Count, RemoteVoteEntity.Resource resource) {
        Error_Cd = error_Cd;
        Error_Nm = error_Nm;
        Total_Count = total_Count;
        Resource = resource;
    }

    public int getError_Cd() {
        return Error_Cd;
    }

    public void setError_Cd(int error_Cd) {
        Error_Cd = error_Cd;
    }

    public String getError_Nm() {
        return Error_Nm;
    }

    public void setError_Nm(String error_Nm) {
        Error_Nm = error_Nm;
    }

    public int getTotal_Count() {
        return Total_Count;
    }

    public void setTotal_Count(int total_Count) {
        Total_Count = total_Count;
    }

    public RemoteVoteEntity.Resource getResource() {
        return Resource;
    }

    public void setResource(RemoteVoteEntity.Resource resource) {
        Resource = resource;
    }

    public static class Resource {
        private String Total_Count;
        private List<Vote_Info_List> Vote_Info_List;

        public Resource(String total_Count, List<RemoteVoteEntity.Resource.Vote_Info_List> vote_Info_List) {
            Total_Count = total_Count;
            Vote_Info_List = vote_Info_List;
        }

        public String getTotal_Count() {
            return Total_Count;
        }

        public void setTotal_Count(String total_Count) {
            Total_Count = total_Count;
        }

        public List<RemoteVoteEntity.Resource.Vote_Info_List> getVote_Info_List() {
            return Vote_Info_List;
        }

        public void setVote_Info_List(List<RemoteVoteEntity.Resource.Vote_Info_List> vote_Info_List) {
            Vote_Info_List = vote_Info_List;
        }

        public static class Vote_Info_List {
            private String Vote_Info_Master_Cd;
            private String Vote_Info_Type;
            private String Vote_Info_Title;
            private String Vote_Info_Description;
            private String Vote_Info_Start_Date;
            private String Vote_Info_End_Date;
            private String Vote_Info_Option_Cnt;
            private String Vote_Info_System;
            private String Status;

            public Vote_Info_List(String vote_Info_Master_Cd, String vote_Info_Type, String vote_Info_Title, String vote_Info_Description, String vote_Info_Start_Date, String vote_Info_End_Date, String vote_Info_Option_Cnt, String vote_Info_System, String status) {
                Vote_Info_Master_Cd = vote_Info_Master_Cd;
                Vote_Info_Type = vote_Info_Type;
                Vote_Info_Title = vote_Info_Title;
                Vote_Info_Description = vote_Info_Description;
                Vote_Info_Start_Date = vote_Info_Start_Date;
                Vote_Info_End_Date = vote_Info_End_Date;
                Vote_Info_Option_Cnt = vote_Info_Option_Cnt;
                Vote_Info_System = vote_Info_System;
                Status = status;
            }

            public String getVote_Info_Master_Cd() {
                return Vote_Info_Master_Cd;
            }

            public void setVote_Info_Master_Cd(String vote_Info_Master_Cd) {
                Vote_Info_Master_Cd = vote_Info_Master_Cd;
            }

            public String getVote_Info_Type() {
                return Vote_Info_Type;
            }

            public void setVote_Info_Type(String vote_Info_Type) {
                Vote_Info_Type = vote_Info_Type;
            }

            public String getVote_Info_Title() {
                return Vote_Info_Title;
            }

            public void setVote_Info_Title(String vote_Info_Title) {
                Vote_Info_Title = vote_Info_Title;
            }

            public String getVote_Info_Description() {
                return Vote_Info_Description;
            }

            public void setVote_Info_Description(String vote_Info_Description) {
                Vote_Info_Description = vote_Info_Description;
            }

            public String getVote_Info_Start_Date() {
                return Vote_Info_Start_Date;
            }

            public void setVote_Info_Start_Date(String vote_Info_Start_Date) {
                Vote_Info_Start_Date = vote_Info_Start_Date;
            }

            public String getVote_Info_End_Date() {
                return Vote_Info_End_Date;
            }

            public void setVote_Info_End_Date(String vote_Info_End_Date) {
                Vote_Info_End_Date = vote_Info_End_Date;
            }

            public String getVote_Info_Option_Cnt() {
                return Vote_Info_Option_Cnt;
            }

            public void setVote_Info_Option_Cnt(String vote_Info_Option_Cnt) {
                Vote_Info_Option_Cnt = vote_Info_Option_Cnt;
            }

            public String getVote_Info_System() {
                return Vote_Info_System;
            }

            public void setVote_Info_System(String vote_Info_System) {
                Vote_Info_System = vote_Info_System;
            }

            public String getStatus() {
                return Status;
            }

            public void setStatus(String status) {
                Status = status;
            }
        }
    }
}

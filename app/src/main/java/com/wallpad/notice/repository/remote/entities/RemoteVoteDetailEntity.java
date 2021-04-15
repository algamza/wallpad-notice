package com.wallpad.notice.repository.remote.entities;

import java.util.List;

public class RemoteVoteDetailEntity {
    private int Error_Cd;
    private String Error_Nm;
    private Resource Resource;

    public RemoteVoteDetailEntity(int error_Cd, String error_Nm, RemoteVoteDetailEntity.Resource resource) {
        Error_Cd = error_Cd;
        Error_Nm = error_Nm;
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

    public RemoteVoteDetailEntity.Resource getResource() {
        return Resource;
    }

    public void setResource(RemoteVoteDetailEntity.Resource resource) {
        Resource = resource;
    }

    public static class Resource {
        private List<Vote_Info_List> Vote_Info_List;
        private String Total_Count;

        public Resource(List<RemoteVoteDetailEntity.Resource.Vote_Info_List> vote_Info_List, String total_Count) {
            Vote_Info_List = vote_Info_List;
            Total_Count = total_Count;
        }

        public List<RemoteVoteDetailEntity.Resource.Vote_Info_List> getVote_Info_List() {
            return Vote_Info_List;
        }

        public void setVote_Info_List(List<RemoteVoteDetailEntity.Resource.Vote_Info_List> vote_Info_List) {
            Vote_Info_List = vote_Info_List;
        }

        public String getTotal_Count() {
            return Total_Count;
        }

        public void setTotal_Count(String total_Count) {
            Total_Count = total_Count;
        }

        public static class Vote_Info_List {
            private String Vote_Info_Master_Seq;
            private String Vote_Info_Detail_Cd;
            private String Vote_Info_Detail_Nm;
            private String Vote_Info_Detail_Description;
            private String Vote_Pick;

            public Vote_Info_List(String vote_Info_Master_Seq, String vote_Info_Detail_Cd, String vote_Info_Detail_Nm, String vote_Info_Detail_Description, String vote_Pick) {
                Vote_Info_Master_Seq = vote_Info_Master_Seq;
                Vote_Info_Detail_Cd = vote_Info_Detail_Cd;
                Vote_Info_Detail_Nm = vote_Info_Detail_Nm;
                Vote_Info_Detail_Description = vote_Info_Detail_Description;
                Vote_Pick = vote_Pick;
            }

            public String getVote_Info_Master_Seq() {
                return Vote_Info_Master_Seq;
            }

            public void setVote_Info_Master_Seq(String vote_Info_Master_Seq) {
                Vote_Info_Master_Seq = vote_Info_Master_Seq;
            }

            public String getVote_Info_Detail_Cd() {
                return Vote_Info_Detail_Cd;
            }

            public void setVote_Info_Detail_Cd(String vote_Info_Detail_Cd) {
                Vote_Info_Detail_Cd = vote_Info_Detail_Cd;
            }

            public String getVote_Info_Detail_Nm() {
                return Vote_Info_Detail_Nm;
            }

            public void setVote_Info_Detail_Nm(String vote_Info_Detail_Nm) {
                Vote_Info_Detail_Nm = vote_Info_Detail_Nm;
            }

            public String getVote_Info_Detail_Description() {
                return Vote_Info_Detail_Description;
            }

            public void setVote_Info_Detail_Description(String vote_Info_Detail_Description) {
                Vote_Info_Detail_Description = vote_Info_Detail_Description;
            }

            public String getVote_Pick() {
                return Vote_Pick;
            }

            public void setVote_Pick(String vote_Pick) {
                Vote_Pick = vote_Pick;
            }
        }
    }
}

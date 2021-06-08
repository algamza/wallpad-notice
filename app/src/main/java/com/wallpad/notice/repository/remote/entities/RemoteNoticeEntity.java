package com.wallpad.notice.repository.remote.entities;

import java.util.List;

public class RemoteNoticeEntity {
    private int Error_Cd;
    private String Error_Nm;
    private Resource Resource;

    public RemoteNoticeEntity(int error_Cd, String error_Nm, RemoteNoticeEntity.Resource resource) {
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

    public RemoteNoticeEntity.Resource getResource() {
        return Resource;
    }

    public void setResource(RemoteNoticeEntity.Resource resource) {
        Resource = resource;
    }

    public static class Resource {
        private int Total_Count;
        private List<Notice_Board_List> Notice_Board_List;

        public Resource(int total_Count, List<Resource.Notice_Board_List> notice_Board_List) {
            Total_Count = total_Count;
            Notice_Board_List = notice_Board_List;
        }

        public int getTotal_Count() {
            return Total_Count;
        }

        public void setTotal_Count(int total_Count) {
            Total_Count = total_Count;
        }

        public List<Resource.Notice_Board_List> getNotice_Board_List() {
            return Notice_Board_List;
        }

        public void setNotice_Board_List(List<Resource.Notice_Board_List> notice_Board_List) {
            Notice_Board_List = notice_Board_List;
        }

        public static class Notice_Board_List {
            private String Notice_Board_Seq;
            private String Notice_Board_Title;
            private String Notice_Board_Contents;
            private String Notice_Board_File_Path;
            private String Reg_Date;

            public Notice_Board_List(String notice_Board_Seq, String notice_Board_Title, String notice_Board_Contents, String notice_Board_File_Path, String reg_Date) {
                Notice_Board_Seq = notice_Board_Seq;
                Notice_Board_Title = notice_Board_Title;
                Notice_Board_Contents = notice_Board_Contents;
                Notice_Board_File_Path = notice_Board_File_Path;
                Reg_Date = reg_Date;
            }

            public String getNotice_Board_Seq() {
                return Notice_Board_Seq;
            }

            public void setNotice_Board_Seq(String notice_Board_Seq) {
                Notice_Board_Seq = notice_Board_Seq;
            }

            public String getNotice_Board_Title() {
                return Notice_Board_Title;
            }

            public void setNotice_Board_Title(String notice_Board_Title) {
                Notice_Board_Title = notice_Board_Title;
            }

            public String getNotice_Board_Contents() {
                return Notice_Board_Contents;
            }

            public void setNotice_Board_Contents(String notice_Board_Contents) {
                Notice_Board_Contents = notice_Board_Contents;
            }

            public String getNotice_Board_File_Path() {
                return Notice_Board_File_Path;
            }

            public void setNotice_Board_File_Path(String notice_Board_File_Path) {
                Notice_Board_File_Path = notice_Board_File_Path;
            }

            public String getReg_Date() {
                return Reg_Date;
            }

            public void setReg_Date(String reg_Date) {
                Reg_Date = reg_Date;
            }
        }
    }
}

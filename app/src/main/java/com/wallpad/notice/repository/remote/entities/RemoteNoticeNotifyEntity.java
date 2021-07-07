package com.wallpad.notice.repository.remote.entities;

public class RemoteNoticeNotifyEntity {
    private String Notice_Board_Seq;
    private String Notice_Board_Title;
    private String Notice_Board_Contents;
    private String Notice_Board_File_Path;
    private String Reg_Date;

    public RemoteNoticeNotifyEntity(String notice_Board_Seq, String notice_Board_Title, String notice_Board_Contents, String notice_Board_File_Path, String reg_Date) {
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

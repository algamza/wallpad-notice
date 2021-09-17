package com.wallpad.notice.repository.common;

import com.wallpad.notice.model.DeliveryModel;
import com.wallpad.notice.model.NoticeModel;
import com.wallpad.notice.model.VisitorModel;
import com.wallpad.notice.model.VoteModel;
import com.wallpad.notice.repository.local.entities.DeliveryEntity;
import com.wallpad.notice.repository.local.entities.DeliveryReadEntity;
import com.wallpad.notice.repository.local.entities.NoticeEntity;
import com.wallpad.notice.repository.local.entities.NoticeReadEntity;
import com.wallpad.notice.repository.local.entities.ReadDeliveryEntity;
import com.wallpad.notice.repository.local.entities.ReadNoticeEntity;
import com.wallpad.notice.repository.local.entities.ReadVisitorEntity;
import com.wallpad.notice.repository.local.entities.ReadVoteEntity;
import com.wallpad.notice.repository.local.entities.VisitorEntity;
import com.wallpad.notice.repository.local.entities.VisitorReadEntity;
import com.wallpad.notice.repository.local.entities.VoteDetailEntity;
import com.wallpad.notice.repository.local.entities.VoteEntity;
import com.wallpad.notice.repository.local.entities.VoteInfoEntity;
import com.wallpad.notice.repository.remote.entities.RemoteNoticeEntity;
import com.wallpad.notice.repository.remote.entities.RemoteNoticeNotifyEntity;
import com.wallpad.notice.repository.remote.entities.RemoteParcelEntity;
import com.wallpad.notice.repository.remote.entities.RemoteParcelNotifyEntity;
import com.wallpad.notice.repository.remote.entities.RemoteVoteDetailEntity;
import com.wallpad.notice.repository.remote.entities.RemoteVoteEntity;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static NoticeModel mapToNoticeModel(NoticeReadEntity entity) {
        NoticeEntity notice = entity.getNotice();
        ReadNoticeEntity read = entity.getRead();
        if ( notice == null ) return null;
        return new NoticeModel(notice.getId(), notice.getTitle(), notice.getContent(), notice.getDate(), notice.getPath(), read!=null);
    }

    public static List<NoticeEntity> mapToEntities(RemoteNoticeEntity notice) {
        List<NoticeEntity> entities = new ArrayList<>();
        if ( notice == null ) return entities;
        RemoteNoticeEntity.Resource resource = notice.getResource();
        int count = resource.getTotal_Count();
        List<RemoteNoticeEntity.Resource.Notice_Board_List> list = resource.getNotice_Board_List();
        if ( list == null || list.size() == 0 ) return entities;
        for (RemoteNoticeEntity.Resource.Notice_Board_List content : list ) {
            if ( content.getNotice_Board_Seq() == null ) continue;
            entities.add(new NoticeEntity(Integer.parseInt(content.getNotice_Board_Seq()), content.getNotice_Board_Title(),
                    content.getNotice_Board_Contents(), content.getReg_Date(), content.getNotice_Board_File_Path()));
        }
        return entities;
    }

    public static NoticeEntity mapToEntity(RemoteNoticeNotifyEntity notice) {
        if ( notice == null ) return null;
        if ( notice.getNotice_Board_Seq() == null ) return null;
        return new NoticeEntity(Integer.parseInt(notice.getNotice_Board_Seq()), notice.getNotice_Board_Title(),
                notice.getNotice_Board_Contents(), notice.getReg_Date(), notice.getNotice_Board_File_Path());
    }

    public static VoteModel mapToModel(VoteEntity entity) {
        List<VoteModel.Detail> detailModels = new ArrayList<>();
        VoteInfoEntity info = entity.getInfo();
        ReadVoteEntity read = entity.getRead();
        List<VoteDetailEntity> details = entity.getDetails();
        for ( VoteDetailEntity detail : details ) {
            detailModels.add(new VoteModel.Detail(detail.getDetailCode(), detail.getTitle(), detail.getDescription(), detail.isVote()));
        }
        return new VoteModel(info.getMasterKey(), mapToVoteType(info.getType()), info.getTitle(), info.getDescription(),
                info.getStartDate(), info.getEndDate(), info.getOptionCount(), mapToVoteSystem(info.getVoteSystem()),
                mapToVoteState(info.getStatus()), read!=null, detailModels);
    }

    public static VoteModel.TYPE mapToVoteType(int type) {
        switch (type) {
            case 0: return VoteModel.TYPE.ALL;
            case 102: return VoteModel.TYPE.SELECT;
        }
        return VoteModel.TYPE.ALL;
    }

    private static VoteModel.SYSTEM mapToVoteSystem(int system) {
        switch (system) {
            case 1: return VoteModel.SYSTEM.SINGLE;
            case 2: return VoteModel.SYSTEM.MULTIPLE;
        }
        return VoteModel.SYSTEM.SINGLE;
    }

    private static VoteModel.STATE mapToVoteState(int state) {
        switch (state) {
            case 0: return VoteModel.STATE.VOTE_BEFORE;
            case 1: return VoteModel.STATE.VOTE_PROGRESS;
            case 2: return VoteModel.STATE.VOTE_END;
            case 3: return VoteModel.STATE.ERROR;
        }
        return VoteModel.STATE.VOTE_BEFORE;
    }

    public static List<VoteDetailEntity> mapToVoteEntities(RemoteVoteDetailEntity entity) {
        List<VoteDetailEntity> votes = new ArrayList<>();
        if ( entity == null ) return votes;
        int errCode = entity.getError_Cd();
        String errNum = entity.getError_Nm();
        RemoteVoteDetailEntity.Resource resource = entity.getResource();
        if ( resource == null ) return votes;
        String count = resource.getTotal_Count();
        List<RemoteVoteDetailEntity.Resource.Vote_Info_List> infos = resource.getVote_Info_List();
        for ( RemoteVoteDetailEntity.Resource.Vote_Info_List info : infos ) {
            votes.add(new VoteDetailEntity(Integer.parseInt(info.getVote_Info_Master_Seq()), Integer.parseInt(info.getVote_Info_Detail_Cd()),
                    info.getVote_Info_Detail_Nm(), info.getVote_Info_Detail_Description(), Integer.parseInt(info.getVote_Pick()) == 1));
        }
        return votes;
    }

    public static VoteModel.Detail mapToVoteDetail(VoteDetailEntity entity) {
        return new VoteModel.Detail(entity.getDetailCode(), entity.getTitle(), entity.getDescription(), entity.isVote());
    }

    public static List<VoteInfoEntity> mapToVoteEntities(RemoteVoteEntity entity) {
        List<VoteInfoEntity> votes = new ArrayList<>();
        if ( entity == null ) return votes;
        int errCode = entity.getError_Cd();
        String errNum = entity.getError_Nm();
        int count = entity.getTotal_Count();
        RemoteVoteEntity.Resource resource = entity.getResource();
        if ( resource == null ) return votes;
        String totalCount = resource.getTotal_Count();
        List<RemoteVoteEntity.Resource.Vote_Info_List> infos = resource.getVote_Info_List();
        if ( infos == null || infos.size() == 0 ) return votes;
        for ( RemoteVoteEntity.Resource.Vote_Info_List info : infos ) {
            votes.add(new VoteInfoEntity(Integer.parseInt(info.getVote_Info_Master_Cd()), Integer.parseInt(info.getVote_Info_Type()),
                    info.getVote_Info_Title(), info.getVote_Info_Description(), info.getVote_Info_Start_Date(),
                    info.getVote_Info_End_Date(), Integer.parseInt(info.getVote_Info_Option_Cnt()),
                    Integer.parseInt(info.getVote_Info_System()), Integer.parseInt(info.getStatus())));
        }
        return votes;
    }

    public static DeliveryModel mapToDeliveryModel(DeliveryReadEntity entity) {
        DeliveryEntity delivery = entity.getDelivery();
        ReadDeliveryEntity read = entity.getRead();
        if ( delivery == null ) return null;
        return new DeliveryModel(delivery.getId(), delivery.getArriveTime(), delivery.getPickupTime(), delivery.getBoxNum(), delivery.isReceipt(), read!=null);
    }

    public static List<DeliveryEntity> mapToDeliveryEntities(RemoteParcelEntity parcel) {
        List<DeliveryEntity> entities = new ArrayList<>();
        if ( parcel == null ) return entities;
        RemoteParcelEntity.Resource resource = parcel.getResource();
        String count = resource.getTotal_Count();
        List<RemoteParcelEntity.Resource.Delivery_Info_List> infos = resource.getDelivery_Info_List();
        if ( infos == null || infos.size() == 0 ) return entities;
        for ( RemoteParcelEntity.Resource.Delivery_Info_List delivery : infos ) {
            entities.add(new DeliveryEntity(getBigKey(Long.parseLong(delivery.getArrive_Time()),
                    Integer.parseInt(delivery.getDelivery_Box_Info())),
                    delivery.getArrive_Time(),
                    delivery.getReceive_Time(),
                    Integer.parseInt(delivery.getDelivery_Box_Info()),
                    Integer.parseInt(delivery.getDelivery_Event_Type()) == 1));
        }
        return entities;
    }

    public static DeliveryEntity mapToDeliveryEntity(RemoteParcelNotifyEntity delivery) {
        if ( delivery == null ) return null;
        return new DeliveryEntity(getBigKey(Long.parseLong(delivery.getEvent_Time()),
                Integer.parseInt(delivery.getDelivery_Box_Info())),
                delivery.getEvent_Time(), "",
                Integer.parseInt(delivery.getDelivery_Box_Info()),
                Integer.parseInt(delivery.getDelivery_Event_Type()) == 1);
    }

    public static List<Long> getDeliveryKeys(List<DeliveryEntity> entities) {
        List<Long> keys = new ArrayList<>();
        for ( DeliveryEntity entity : entities ) {
            keys.add(getBigKey(Long.parseLong(entity.getArriveTime()), entity.getBoxNum()));
        }
        return keys;
    }

    public static List<Integer> getVoteKeys(List<VoteInfoEntity> entities) {
        List<Integer> keys = new ArrayList<>();
        for ( VoteInfoEntity entity : entities ) {
            keys.add(entity.getMasterKey());
        }
        return keys;
    }

    public static List<Integer> getNoticeIds(List<NoticeEntity> entities) {
        List<Integer> keys = new ArrayList<>();
        for ( NoticeEntity entity : entities ) {
            keys.add(entity.getId());
        }
        return keys;
    }

    public static VisitorModel mapToVisitorModel(VisitorReadEntity entity) {
        VisitorEntity visitor = entity.getVisitor();
        ReadVisitorEntity read = entity.getRead();
        if ( visitor == null ) return null;
        return new VisitorModel(visitor.getId(), visitor.getScreen(), visitor.getPlace(), visitor.getDate(), read!=null);
    }

    public static List<String> getVisitorIds(List<VisitorEntity> entities) {
        List<String> keys = new ArrayList<>();
        for ( VisitorEntity entity : entities ) {
            keys.add(entity.getId());
        }
        return keys;
    }

    public static long getBigKey(long group, int channel) {
        return group*1000+channel;
    }
}

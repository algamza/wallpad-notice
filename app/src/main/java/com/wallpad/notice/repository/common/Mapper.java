package com.wallpad.notice.repository.common;

import android.util.Log;

import com.wallpad.notice.model.DeliveryModel;
import com.wallpad.notice.model.NoticeModel;
import com.wallpad.notice.model.VoteModel;
import com.wallpad.notice.model.VisitorModel;
import com.wallpad.notice.repository.local.entities.DeliveryEntity;
import com.wallpad.notice.repository.local.entities.NoticeEntity;
import com.wallpad.notice.repository.local.entities.VoteDetailEntity;
import com.wallpad.notice.repository.local.entities.VoteEntity;
import com.wallpad.notice.repository.local.entities.VisitorEntity;
import com.wallpad.notice.repository.local.entities.VoteInfoEntity;
import com.wallpad.notice.repository.remote.entities.RemoteParcelEntity;
import com.wallpad.notice.repository.remote.entities.RemoteVoteDetailEntity;
import com.wallpad.notice.repository.remote.entities.RemoteVoteEntity;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static NoticeModel mapToNoticeModel(NoticeEntity entity) {
        return new NoticeModel(entity.getId(), entity.getTitle(), entity.getContent(), entity.getDate(), entity.isRead());
    }
    public static NoticeEntity mapToNoticeEntity(NoticeModel model) {
        return new NoticeEntity(model.getId(), model.getTitle(), model.getContent(), model.getDate(), model.isRead());
    }

    public static VoteModel mapToModel(VoteEntity entity) {
        List<VoteModel.Detail> detailModels = new ArrayList<>();
        VoteInfoEntity info = entity.getInfo();
        List<VoteDetailEntity> details = entity.getDetails();
        for ( VoteDetailEntity detail : details ) {
            detailModels.add(new VoteModel.Detail(detail.getDetailCode(), detail.getTitle(), detail.getDescription(), detail.isVote()));
        }
        return new VoteModel(info.getMasterKey(), mapToVoteType(info.getType()), info.getTitle(), info.getDescription(),
                info.getStartDate(), info.getEndDate(), info.getOptionCount(), mapToVoteSystem(info.getVoteSystem()),
                mapToVoteState(info.getStatus()), info.isRead(), detailModels);
    }

    private static VoteModel.TYPE mapToVoteType(int type) {
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
                    info.getVote_Info_Detail_Nm(), info.getVote_Info_Detail_Description(), Integer.parseInt(info.getVote_Pick()) == 0));
        }
        return votes;
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
                    Integer.parseInt(info.getVote_Info_System()), Integer.parseInt(info.getStatus()), false));
        }
        return votes;
    }

    public static DeliveryModel mapToDeliveryModel(DeliveryEntity entity) {
        return new DeliveryModel(entity.getId(), entity.getArriveTime(), entity.getPickupTime(), entity.getBoxNum(), entity.isReceipt(), entity.isRead());
    }
    public static DeliveryEntity mapToDeliveryEntity(DeliveryModel model) {
        return new DeliveryEntity(model.getId(), model.getPickupTime(), model.getArriveTime(), model.getBoxNum(), model.isReceipt(), model.isRead());
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
                    Integer.parseInt(delivery.getDelivery_Event_Type()) == 1,
                    false));
        }
        return entities;
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

    public static VisitorModel mapToVisitorModel(VisitorEntity entity) {
        return new VisitorModel(entity.getId(), entity.getScreen(), entity.getPlace(), entity.getDate(), entity.isRead());
    }
    public static VisitorEntity mapToVisitorEntity(VisitorModel model) {
        return new VisitorEntity(model.getId(), model.getScreen(), model.getPlace(), model.getDate(), model.isRead());
    }

    public static long getBigKey(long group, int channel) {
        return group*1000+channel;
    }
}

import request from '@/utils/request'
    
export default{
    getGoodList(searchModel){
        return request({
            url:'history/list',
            method:'get',
            params:{
                pageNo:searchModel.pageNo,
                pageSize:searchModel.pageSize,
                goodid:searchModel.goodid,
                userid:searchModel.userid
            }
        });
    },
}

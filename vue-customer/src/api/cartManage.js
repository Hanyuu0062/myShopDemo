import request from '@/utils/request'
    
export default{
    addGood(cartForm){
        return request({
            url:'cart/goodadd',
            method:'post',
            data: cartForm
        });
    },

    getGoodList(searchModel){
        return request({
            url:'cart/cartlist',
            method:'get',
            params:{
                pageNo:searchModel.pageNo,
                pageSize:searchModel.pageSize,
                goodid:searchModel.goodid,
                userid:searchModel.userid
            }
        });
    },

    buy(id){
        return request({
            url:'cart/buy',
            method:'get',
            params:{
                id:id
            }
        });
    },

    updateGood(cartForm){
        return request({
            url:'cart/update',
            method:'post',
            data: cartForm
        });
    },

    deleteGood(id){
        return request({
            url:`cart/gooddel${id}`,
            method:'delete',
        });
    },

}

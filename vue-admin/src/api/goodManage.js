import request from '@/utils/request'
    
export default{
    getGoodList(searchModel){
        return request({
            url:'boss/goodslist',
            method:'get',
            params:{
                pageNo:searchModel.pageNo,
                pageSize:searchModel.pageSize,
                goodname:searchModel.goodname,
                goodid:searchModel.goodid
            }
        });
    },

    addGood(goodForm){
        return request({
            url:'boss/goodadd',
            method:'post',
            data: goodForm
        });
    },

    getGoodById(id){
        return request({
            url:`boss/idgood`, 
            method:'get',
            params:{
                id: id
            }
        });
    },

    saveGood(good){
        if(good.id==null||good.id==undefined){
            return this.addGood(good);
        }else{
            return this.updateGood(good);
        }
    },

    updateGood(goodForm){
        return request({
            url:'boss/goodupdate',
            method:'post',
            data: goodForm
        });
    },

    deleteGood(id){
        return request({
            url:`boss/gooddel${id}`,
            method:'delete',
        });
    }
}

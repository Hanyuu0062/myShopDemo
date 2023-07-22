import request from '@/utils/request'
    
export default{
    getUserList(searchModel){
        return request({
            url:'boss/userslist',
            method:'get',
            params:{
                pageNo:searchModel.pageNo,
                pageSize:searchModel.pageSize,
                username:searchModel.username,
                userid:searchModel.userid
            }
        });
    },

    getUserById(id){
        return request({
            url:`boss/iduser`, 
            method:'get',
            params:{
                id: id
            }
        });
    },

    updateUser(userForm){
        return request({
            url:'boss/userupdate',
            method:'post',
            data: userForm
        });
    },

    deleteUser(id){
        return request({
            url:`boss/userdel${id}`,
            method:'delete',
        });
    }

}

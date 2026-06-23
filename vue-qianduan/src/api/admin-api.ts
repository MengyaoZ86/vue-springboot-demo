/*
 *  管理员信息
 */

import { post, get } from '@/utils/request';
export const adminApi = {
    // 新增管理员信息
    add: (param:any) => {
        return post('/admin/add', param);
    },
    // 修改管理员信息
    update: (param:any) => {
        return post('/admin/update', param);
    },
    // 删除管理员信息
    delete: (id:any) => {
    return post(`/admin/delete?id=${id}`, {});
},
 
    // 分页查询管理员信息列表
    //queryPageList: (param:any,pagenum:number,pagesize:number) => {
    //    return post('/admin/list?pagenum=' + pagenum + '&pagesize=' + pagesize,param);
    queryPageList: (param: any, pageNum: number, pageSize: number) => {
    return post(
      `/admin/list?pageNum=${pageNum}&pageSize=${pageSize}`,
      param
    )
  }

};
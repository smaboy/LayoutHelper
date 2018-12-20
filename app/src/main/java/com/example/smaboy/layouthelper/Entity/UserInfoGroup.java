package com.example.smaboy.layouthelper.Entity;

import java.util.List;

/**
 * 类名: UserInfoGroup
 * 类作用描述: java类作用描述
 * 作者: Smaboy
 * 创建时间: 2018/12/19 17:32
 */
public class UserInfoGroup {

    /**
     * id : 1
     * title : 用户信息
     * userinfogroup : [{"grouptitle":"分组1","users":[{"name":"姓名","age":23,"phone":18600010001},{"name":"姓名","age":23,"phone":18600010001}]},{"grouptitle":"分组1","users":[{"name":"姓名","age":23,"phone":18600010001},{"name":"姓名","age":23,"phone":18600010001}]},{"grouptitle":"分组1","users":[{"name":"姓名","age":23,"phone":18600010001},{"name":"姓名","age":23,"phone":18600010001}]},{"grouptitle":"分组1","users":[{"name":"姓名","age":23,"phone":18600010001},{"name":"姓名","age":23,"phone":18600010001}]}]
     */

    private int id;
    private String title;
    private List<UserinfogroupBean> userinfogroup;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<UserinfogroupBean> getUserinfogroup() {
        return userinfogroup;
    }

    public void setUserinfogroup(List<UserinfogroupBean> userinfogroup) {
        this.userinfogroup = userinfogroup;
    }

    public static class UserinfogroupBean {
        /**
         * grouptitle : 分组1
         * users : [{"name":"姓名","age":23,"phone":18600010001},{"name":"姓名","age":23,"phone":18600010001}]
         */

        private String grouptitle;
        private List<UsersBean> users;

        @Override
        public String toString() {
            return "UserinfogroupBean{" +
                    "grouptitle='" + grouptitle + '\'' +
                    ", users=" + users +
                    '}';
        }

        public String getGrouptitle() {
            return grouptitle;
        }

        public void setGrouptitle(String grouptitle) {
            this.grouptitle = grouptitle;
        }

        public List<UsersBean> getUsers() {
            return users;
        }

        public void setUsers(List<UsersBean> users) {
            this.users = users;
        }

        public static class UsersBean {
            /**
             * name : 姓名
             * age : 23
             * phone : 18600010001
             */

            private String name;
            private int age;
            private long phone;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public long getPhone() {
                return phone;
            }

            public void setPhone(long phone) {
                this.phone = phone;
            }

            @Override
            public String toString() {
                return "UsersBean{" +
                        "name='" + name + '\'' +
                        ", age=" + age +
                        ", phone=" + phone +
                        '}';
            }
        }
    }

    @Override
    public String toString() {
        return "UserInfoGroup{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", userinfogroup=" + userinfogroup +
                '}';
    }
}

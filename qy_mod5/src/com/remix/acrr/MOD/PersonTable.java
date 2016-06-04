package com.remix.acrr.MOD;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import android.Manifest.permission;

@Table(name="person")  
public class PersonTable {  
    @Column(name="id",isId=true,autoGen=true)  
    private int id;  
    //姓名  
    @Column(name="name")  
    private String name;  
  
    //年龄  
    @Column(name="age")  
    private int age;  
  
    //性别  
    @Column(name="sex")  
    private String sex;  
  
    //工资  
    @Column(name="salary")  
    private String salary;  
  
    public PersonTable(){
    	
    }
    public PersonTable(String name, int age, String sex, String salary){
    	this.name = name;
    	this.age = age;
    	this.sex = sex;
    	this.salary = salary;
    }
    public int getId() {  
        return id;  
    }  
  
    public void setId(int id) {  
        this.id = id;  
    }  
  
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
  
  
    public String getSex() {  
        return sex;  
    }  
  
    public void setSex(String sex) {  
        this.sex = sex;  
    }  
  
    public int getAge() {  
        return age;  
    }  
  
    public void setAge(int age) {  
        this.age = age;  
    }  
  
  
    public String getSalary() {  
        return salary;  
    }  
  
    public void setSalary(String salary) {  
        this.salary = salary;  
    }  
  
    @Override  
    public String toString() {  
        return "PersonTable [id=" + id + ", name=" + name + ", age=" + age  
                + ", sex=" + sex + ", salary=" + salary + "]";  
    }  
}  

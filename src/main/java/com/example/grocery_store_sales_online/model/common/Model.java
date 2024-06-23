package com.example.grocery_store_sales_online.model.common;

import com.example.grocery_store_sales_online.model.common.IModel;
import com.google.common.primitives.Longs;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;
import jakarta.persistence.*;

@Getter
@Setter
@MappedSuperclass
public class Model implements IModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String personEdit;
    private String personCreate;
    private Date createDate;
    private Date editDate;
    private boolean deleted;

    public Model() {
    }

    public Model(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        boolean res;
        if(obj==null || noId()){
            res=this==obj;
        }else {
            final Class<?> class1=getClass();
            final Class<?> class2=obj.getClass();
            if(class1.equals(class2) || class1!= IModel.class && class1.isInstance(obj)
            || obj instanceof IModel && class2!= IModel.class && class2.isInstance(this)){
                final Object id2= getId();
                assert id2!=null;
                res= id2.equals(((IModel) obj).getId());
            }else {
                res =false;
            }
        }
        return res;
    }

    @Override
    public int hashCode() {
        int res;
        if(noId()){
            res=supperHashCode();
        }else {
            final Object id2=getId();
            assert id2!= null;
            res = Longs.hashCode((Long) id2);
        }
        return res;
    }

    @Override
    public boolean inUse() {
        return false;
    }

    @Transient
    @Override
    public boolean noId() {
        return getId()==null || getId().equals(0L);
    }

    @Override
    public String toString() {
        return super.toString() + " : " + getId();
    }
    public  int supperHashCode(){
        return super.hashCode();
    }
}

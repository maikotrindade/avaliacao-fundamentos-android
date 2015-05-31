package com.example.administrador.myapplication.models.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.administrador.myapplication.R;
import com.example.administrador.myapplication.models.persistence.ServiceOrdersRepository;
import com.example.administrador.myapplication.util.AppUtil;

import java.util.Date;
import java.util.List;

public class ServiceOrder implements Parcelable {

    private Integer mId;
    private String mClient;
    private String mPhone;
    private String mAddress;
    private Date mDate;
    private double mValue;
    private boolean mPaid;
    private String mDescription;
    private boolean mActive;
    private ServiceOrderCategory mCategory;

    public enum ServiceOrderCategory {

        UNCATEGORISED (0, R.string.lbl_uncategorised),
        IMPROVEMENTS (1, R.string.lbl_improvements),
        SUPPORT (2, R.string.lbl_support),
        MAINTENANCE (3, R.string.lbl_maintenance),
        CONSULTANCY (4, R.string.lbl_consultancy);

        final private int id;
        final private int resource;

        ServiceOrderCategory(int id, int resource) {
            this.id = id;
            this.resource = resource;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return AppUtil.CONTEXT.getResources().getString(resource);
        }
    }


    public ServiceOrder() {
        super();
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public String getClient() {
        return mClient;
    }

    public void setClient(String client) {
        this.mClient = client;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        this.mPhone = phone;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        this.mAddress = address;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }

    public double getValue() {
        return mValue;
    }

    public void setValue(double value) {
        this.mValue = value;
    }

    public boolean isPaid() {
        return mPaid;
    }

    public void setPaid(boolean paid) {
        this.mPaid = paid;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public boolean isActive() {
        return mActive;
    }

    public void setActive(boolean mActive) {
        this.mActive = mActive;
    }

    public Integer getCategory() {
        return mCategory.getId();
    }

    public void setCategory(int categoryId) {
        switch (categoryId) {
            case 0 : this.mCategory =  ServiceOrderCategory.UNCATEGORISED; break;
            case 1 : this.mCategory =  ServiceOrderCategory.IMPROVEMENTS; break;
            case 2 : this.mCategory =  ServiceOrderCategory.SUPPORT; break;
            case 3 : this.mCategory =  ServiceOrderCategory.MAINTENANCE; break;
            case 4 : this.mCategory =  ServiceOrderCategory.CONSULTANCY; break;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceOrder that = (ServiceOrder) o;

        if (Double.compare(that.mValue, mValue) != 0) return false;
        if (mPaid != that.mPaid) return false;
        if (mId != null ? !mId.equals(that.mId) : that.mId != null) return false;
        if (mClient != null ? !mClient.equals(that.mClient) : that.mClient != null) return false;
        if (mPhone != null ? !mPhone.equals(that.mPhone) : that.mPhone != null) return false;
        if (mAddress != null ? !mAddress.equals(that.mAddress) : that.mAddress != null)
            return false;
        if (mDate != null ? !mDate.equals(that.mDate) : that.mDate != null) return false;
        return !(mDescription != null ? !mDescription.equals(that.mDescription) : that.mDescription != null);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = mId != null ? mId.hashCode() : 0;
        result = 31 * result + (mClient != null ? mClient.hashCode() : 0);
        result = 31 * result + (mPhone != null ? mPhone.hashCode() : 0);
        result = 31 * result + (mAddress != null ? mAddress.hashCode() : 0);
        result = 31 * result + (mDate != null ? mDate.hashCode() : 0);
        temp = Double.doubleToLongBits(mValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (mPaid ? 1 : 0);
        result = 31 * result + (mDescription != null ? mDescription.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + mId +
                ", \"client\": \"" + mClient + '\"' +
                ", \"phone\": \"" + mPhone + '\"' +
                ", \"address\": \"" + mAddress + '\"' +
                ", \"date\":" + (mDate == null ? 0 : mDate.getTime()) +
                ", \"value\":" + mValue +
                ", \"paid\":" + mPaid +
                ", \"description\": \"" + mDescription + '\"' +
                ", \"active\": \"" + mActive + '\"' +
                ", \"active\": \"" + mCategory.toString() + '\"' +
                "}";
    }

    public static List<ServiceOrder> getAll() {
        return ServiceOrdersRepository.getInstance().getAll();
    }

    public static List<ServiceOrder> getAllByStatus(boolean filterActive) {
        return ServiceOrdersRepository.getInstance().getAllByStatus(filterActive);
    }

    public void save() {
        ServiceOrdersRepository.getInstance().save(this);
    }

    public void delete() {
        ServiceOrdersRepository.getInstance().delete(this);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mId);
        dest.writeString(this.mClient);
        dest.writeString(this.mPhone);
        dest.writeString(this.mAddress);
        dest.writeLong(mDate != null ? mDate.getTime() : -1);
        dest.writeDouble(this.mValue);
        dest.writeByte(mPaid ? (byte) 1 : (byte) 0);
        dest.writeString(this.mDescription);
        dest.writeByte(mActive ? (byte) 1 : (byte) 0);
        dest.writeInt(this.mCategory == null ? -1 : this.mCategory.ordinal());
    }

    private ServiceOrder(Parcel in) {
        this.mId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mClient = in.readString();
        this.mPhone = in.readString();
        this.mAddress = in.readString();
        long tmpMDate = in.readLong();
        this.mDate = tmpMDate == -1 ? null : new Date(tmpMDate);
        this.mValue = in.readDouble();
        this.mPaid = in.readByte() != 0;
        this.mDescription = in.readString();
        this.mActive = in.readByte() != 0;
        int tmpMCategory = in.readInt();
        this.mCategory = tmpMCategory == -1 ? null : ServiceOrderCategory.values()[tmpMCategory];
    }

    public static final Parcelable.Creator<ServiceOrder> CREATOR = new Parcelable.Creator<ServiceOrder>() {
        public ServiceOrder createFromParcel(Parcel source) {
            return new ServiceOrder(source);
        }

        public ServiceOrder[] newArray(int size) {
            return new ServiceOrder[size];
        }
    };
}

package com.penpower.worldcard.team.mongo.document;

import com.penpower.worldcard.team.Utils.StringUtil;
import com.penpower.worldcard.team.enums.ContactFieldType;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "address")
@TypeAlias("address")
@Data
public class ContactAddress extends BaseOrderDocument {
    private String countryCode;
    private String countryName;
    private String street;
    private String city;
    private String state;
    private String zip;
    private Integer addressFormat;
    private ContactFieldType addressType;

    public String GetFullAddress() {
        String wComma = ",";
        String wSpace = " ";
        String wCommaSpace =", ";
        String wPostSym = "\u3012";
        String fullAddress ="";
        switch(addressFormat)
        {
            // 中文
            // CountryProvinceCityStreet, Postalcode
            case 1:
                fullAddress = CheckAndAppend(fullAddress,countryName);
                fullAddress = CheckAndAppend(fullAddress,state);
                fullAddress = CheckAndAppend(fullAddress,city);
                fullAddress = CheckAndAppend(fullAddress,street);
                fullAddress = CheckAndAppend(fullAddress,countryName);
                if(!StringUtil.IsStringNullorEmpty(zip))
                {
                    fullAddress =  wcscat(fullAddress, wCommaSpace);
                    fullAddress =  wcscat(fullAddress, zip);
                }
                break;
            // 英文
            // Street, City, Province Postalcode Country
            default:
            case 0:
            case 2:
                if(!StringUtil.IsStringNullorEmpty(street))
                {
                    fullAddress = CheckAndAppend(fullAddress,street);

                    if(!StringUtil.IsStringNullorEmpty(city) || !StringUtil.IsStringNullorEmpty(state))
                        fullAddress =  wcscat(fullAddress, wCommaSpace);
                    else if(!StringUtil.IsStringNullorEmpty(zip))
                        fullAddress =  wcscat(fullAddress, wSpace);
                    else if(!StringUtil.IsStringNullorEmpty(countryName))
                        fullAddress =  wcscat(fullAddress, wCommaSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(city))
                {
                    fullAddress =  wcscat(fullAddress, city);
                    if(!StringUtil.IsStringNullorEmpty(state))
                        fullAddress =  wcscat(fullAddress, wCommaSpace);
                    else if(!StringUtil.IsStringNullorEmpty(zip))
                        fullAddress =  wcscat(fullAddress, wSpace);
                    else if(!StringUtil.IsStringNullorEmpty(countryName))
                        fullAddress =  wcscat(fullAddress, wCommaSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(state))
                {
                    fullAddress =  wcscat(fullAddress, state);
                    if(!StringUtil.IsStringNullorEmpty(zip))
                        fullAddress =  wcscat(fullAddress, wSpace);
                    else if(!StringUtil.IsStringNullorEmpty(countryName))
                        fullAddress =  wcscat(fullAddress, wCommaSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(zip))
                {
                    fullAddress =  wcscat(fullAddress, zip);
                    if(!StringUtil.IsStringNullorEmpty(countryName))
                        fullAddress =  wcscat(fullAddress, wSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(countryName))
                    fullAddress =  wcscat(fullAddress, countryName);
                break;
            // 歐文
            // Street, Postalcode City Province Country
            case 3:
                if(!StringUtil.IsStringNullorEmpty(street))
                {
                    fullAddress =  wcscat(fullAddress, street);
                    if(!StringUtil.IsStringNullorEmpty(zip) || !StringUtil.IsStringNullorEmpty(city) || !StringUtil.IsStringNullorEmpty(state) || !StringUtil.IsStringNullorEmpty(countryName))
                        fullAddress =  wcscat(fullAddress, wCommaSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(zip))
                {
                    fullAddress =  wcscat(fullAddress, zip);
                    if(!StringUtil.IsStringNullorEmpty(city) || !StringUtil.IsStringNullorEmpty(state) || !StringUtil.IsStringNullorEmpty(countryName))
                        fullAddress =  wcscat(fullAddress, wSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(city))
                {
                    fullAddress =  wcscat(fullAddress, city);
                    if(!StringUtil.IsStringNullorEmpty(state) || !StringUtil.IsStringNullorEmpty(countryName))
                        fullAddress =  wcscat(fullAddress, wSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(state))
                {
                    fullAddress =  wcscat(fullAddress, state);
                    if(!StringUtil.IsStringNullorEmpty(countryName))
                        fullAddress =  wcscat(fullAddress, wSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(countryName))
                    fullAddress =  wcscat(fullAddress, countryName);
                break;
            // 日文
            // 〒Postalcode CountryProvinceCityStreet
            case 4:
                if(!StringUtil.IsStringNullorEmpty(zip))
                {
                    fullAddress =  wcscat(fullAddress, wPostSym);
                    fullAddress =  wcscat(fullAddress, zip);
                    fullAddress =  wcscat(fullAddress, wSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(countryName))
                    fullAddress =  wcscat(fullAddress, countryName);
                if(!StringUtil.IsStringNullorEmpty(state))
                    fullAddress =  wcscat(fullAddress, state);
                if(!StringUtil.IsStringNullorEmpty(city))
                    fullAddress =  wcscat(fullAddress, city);
                if(!StringUtil.IsStringNullorEmpty(street))
                    fullAddress =  wcscat(fullAddress, street);
                break;
            // 韓文
            // Postalcode CountryProvinceCityStreet
            case 5:
                if(!StringUtil.IsStringNullorEmpty(zip))
                {
                    fullAddress =  wcscat(fullAddress, zip);
                    fullAddress =  wcscat(fullAddress, wSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(countryName))
                    fullAddress =  wcscat(fullAddress, countryName);
                if(!StringUtil.IsStringNullorEmpty(state))
                    fullAddress =  wcscat(fullAddress, state);
                if(!StringUtil.IsStringNullorEmpty(city))
                    fullAddress =  wcscat(fullAddress, city);
                if(!StringUtil.IsStringNullorEmpty(street))
                    fullAddress =  wcscat(fullAddress, street);
                break;
            // 新加坡
            // Street Country Postalcode
            case 6:
                if(!StringUtil.IsStringNullorEmpty(street))
                {
                    fullAddress =  wcscat(fullAddress, street);
                    if(!StringUtil.IsStringNullorEmpty(countryName) || !StringUtil.IsStringNullorEmpty(zip))
                        fullAddress =  wcscat(fullAddress, wSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(countryName))
                {
                    fullAddress =  wcscat(fullAddress, countryName);
                    if(!StringUtil.IsStringNullorEmpty(zip))
                        fullAddress =  wcscat(fullAddress, wSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(zip))
                    fullAddress =  wcscat(fullAddress, zip);
                break;
            // 印度
            // Street, City, Postalcode Province, Country
            case 7:
                if(!StringUtil.IsStringNullorEmpty(street))
                {
                    fullAddress =  wcscat(fullAddress, street);
                    if(!StringUtil.IsStringNullorEmpty(city) || !StringUtil.IsStringNullorEmpty(zip) || !StringUtil.IsStringNullorEmpty(state) || !StringUtil.IsStringNullorEmpty(countryName))
                        fullAddress =  wcscat(fullAddress, wCommaSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(city))
                {
                    fullAddress =  wcscat(fullAddress, city);
                    if(!StringUtil.IsStringNullorEmpty(zip) || !StringUtil.IsStringNullorEmpty(state) || !StringUtil.IsStringNullorEmpty(countryName))
                        fullAddress =  wcscat(fullAddress, wCommaSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(zip))
                {
                    fullAddress =  wcscat(fullAddress, zip);
                    if(!StringUtil.IsStringNullorEmpty(state) || !StringUtil.IsStringNullorEmpty(countryName))
                        fullAddress =  wcscat(fullAddress, wSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(state))
                {
                    fullAddress =  wcscat(fullAddress, state);
                    if(!StringUtil.IsStringNullorEmpty(countryName))
                        fullAddress =  wcscat(fullAddress, wCommaSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(countryName))
                    fullAddress =  wcscat(fullAddress, countryName);
                break;
            // 加拿大
            // Street, City Province, Country Postalcode
            case 8:
                if(!StringUtil.IsStringNullorEmpty(street))
                {
                    fullAddress =  wcscat(fullAddress, street);
                    if(!StringUtil.IsStringNullorEmpty(city) || !StringUtil.IsStringNullorEmpty(state) || !StringUtil.IsStringNullorEmpty(countryName))
                        fullAddress =  wcscat(fullAddress, wCommaSpace);
                    else if(!StringUtil.IsStringNullorEmpty(zip))
                        fullAddress =  wcscat(fullAddress, wSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(city))
                {
                    fullAddress =  wcscat(fullAddress, city);
                    if(!StringUtil.IsStringNullorEmpty(state) || !StringUtil.IsStringNullorEmpty(countryName))
                        fullAddress =  wcscat(fullAddress, wCommaSpace);
                    else if(!StringUtil.IsStringNullorEmpty(zip))
                        fullAddress =  wcscat(fullAddress, wSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(state))
                {
                    fullAddress =  wcscat(fullAddress, state);
                    if(!StringUtil.IsStringNullorEmpty(countryName))
                        fullAddress =  wcscat(fullAddress, wCommaSpace);
                    else if(!StringUtil.IsStringNullorEmpty(zip))
                        fullAddress =  wcscat(fullAddress, wSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(countryName))
                {
                    fullAddress =  wcscat(fullAddress, countryName);
                    if(!StringUtil.IsStringNullorEmpty(zip))
                        fullAddress =  wcscat(fullAddress, wSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(zip))
                    fullAddress =  wcscat(fullAddress, zip);
                break;
            // 俄羅斯
            // Country Postalcode Province, City, Street
            case 9:
                if(!StringUtil.IsStringNullorEmpty(countryName))
                {
                    fullAddress =  wcscat(fullAddress, countryName);
                    if(!StringUtil.IsStringNullorEmpty(zip) || !StringUtil.IsStringNullorEmpty(state) || !StringUtil.IsStringNullorEmpty(city) || !StringUtil.IsStringNullorEmpty(street))
                        fullAddress =  wcscat(fullAddress, wSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(zip))
                {
                    fullAddress =  wcscat(fullAddress, zip);
                    if(!StringUtil.IsStringNullorEmpty(state) || !StringUtil.IsStringNullorEmpty(city) || !StringUtil.IsStringNullorEmpty(street))
                        fullAddress =  wcscat(fullAddress, wSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(state))
                {
                    fullAddress =  wcscat(fullAddress, state);
                    if(!StringUtil.IsStringNullorEmpty(city) || !StringUtil.IsStringNullorEmpty(street))
                        fullAddress =  wcscat(fullAddress, wCommaSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(city))
                {
                    fullAddress =  wcscat(fullAddress, city);
                    if(!StringUtil.IsStringNullorEmpty(street))
                        fullAddress =  wcscat(fullAddress, wCommaSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(street))
                    fullAddress =  wcscat(fullAddress, street);
                break;
            // 匈牙利
            // Postalcode City, Province, Street, Country
            case 10:
                if(!StringUtil.IsStringNullorEmpty(zip))
                {
                    fullAddress =  wcscat(fullAddress, zip);
                    if(!StringUtil.IsStringNullorEmpty(city) || !StringUtil.IsStringNullorEmpty(state) || !StringUtil.IsStringNullorEmpty(street) || !StringUtil.IsStringNullorEmpty(countryName))
                        fullAddress =  wcscat(fullAddress, wSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(city))
                {
                    fullAddress =  wcscat(fullAddress, city);
                    if(!StringUtil.IsStringNullorEmpty(state) || !StringUtil.IsStringNullorEmpty(street) || !StringUtil.IsStringNullorEmpty(countryName))
                        fullAddress =  wcscat(fullAddress, wCommaSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(state))
                {
                    fullAddress =  wcscat(fullAddress, state);
                    if(!StringUtil.IsStringNullorEmpty(street) || !StringUtil.IsStringNullorEmpty(countryName))
                        fullAddress =  wcscat(fullAddress, wCommaSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(street))
                {
                    fullAddress =  wcscat(fullAddress, street);
                    if(!StringUtil.IsStringNullorEmpty(countryName))
                        fullAddress =  wcscat(fullAddress, wCommaSpace);
                }
                if(!StringUtil.IsStringNullorEmpty(countryName))
                    fullAddress =  wcscat(fullAddress, countryName);
                break;
        }
        return fullAddress;
    }

    private static String CheckAndAppend(String Src,String Target) {
        if(!StringUtil.IsStringNullorEmpty(Target))
            Src += Target;
        return Src;
    }

    private static String wcscat(String Src,String Target) {
        Src += Target;
        return Src;
    }
}

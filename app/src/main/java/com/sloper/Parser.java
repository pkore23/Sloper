package com.sloper;

import com.sloper.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pradnesh Kore on 08-07-2016.
 */
public class Parser {

    public static List<DummyContent.DummyItem> addAll() {
        ArrayList<DummyContent.DummyItem> products = new ArrayList<>();
        DummyContent.DummyItem product;
        File fPath = MainActivity.mContext.getExternalFilesDir(null);
        File dataFile = new File(fPath,"json_data.json");
        if(!dataFile.exists()){
            product = new DummyContent.DummyItem("p_01", "No Data Found", "Please wait for refresh", "",  "", "", "No data found");
            products.add(product);
            return products;
        }
        else{
            try {
                FileInputStream fis = new FileInputStream(dataFile);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                StringBuilder buffer=new StringBuilder("");
                String line;
                while((line=br.readLine())!=null){
                    buffer.append(line);
                }
                JSONObject root = new JSONObject(buffer.toString());
                JSONArray productsJson = root.getJSONArray("products");
                int i=0;
                for(i=0;i<productsJson.length();i++){
                    JSONObject obj = (JSONObject) productsJson.get(i);
                    String pid = obj.getString("pid");
                    String name = obj.getString("name");
                    String cat = obj.getString("type");
                    String price = obj.getString("price");
                    String colors = obj.getString("color");
                    String sizes = obj.getString("sizes");
                    String description = obj.getString("description");
                    product = new DummyContent.DummyItem(pid, name, cat, price, colors, sizes, description);
                    products.add(product);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                product = new DummyContent.DummyItem("p_01", "No Data Found", "Please wait for refresh", "", "", "", "No data found");
                products.add(product);
                return products;
            } catch (IOException e) {
                e.printStackTrace();
                product = new DummyContent.DummyItem("p_01", "No Data Found", "Please wait for refresh", "",  "", "", "No data found");
                products.add(product);
                return products;
            } catch (JSONException e) {
                e.printStackTrace();
                product = new DummyContent.DummyItem("p_01", "No Data Found", "Please wait for refresh", "",  "", "", "No data found");
                products.add(product);
                return products;
            }
        }
        return products;
    }

    public static List<DummyContent.DummyItem> addCategory1() {
        ArrayList<DummyContent.DummyItem> products = new ArrayList<>();
        DummyContent.DummyItem product;
        File fPath = MainActivity.mContext.getExternalFilesDir(null);
        File dataFile = new File(fPath,"json_data.json");
        if(!dataFile.exists()){
            product = new DummyContent.DummyItem("p_01", "No Data Found", "Please wait for refresh", "",  "", "", "No data found");
            products.add(product);
            return products;
        }
        else{
            try {
                FileInputStream fis = new FileInputStream(dataFile);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                StringBuilder buffer=new StringBuilder("");
                String line;
                while((line=br.readLine())!=null){
                    buffer.append(line);
                }
                JSONObject root = new JSONObject(buffer.toString());
                JSONArray productsJson = root.getJSONArray("products");
                int i=0;
                for(i=0;i<productsJson.length();i++){
                    JSONObject obj = (JSONObject) productsJson.get(i);
                    String cat = obj.getString("type");
                    if(cat.equalsIgnoreCase("formal trouser")) {
                        String pid = obj.getString("pid");
                        String name = obj.getString("name");
                        String price = obj.getString("price");
                        String colors = obj.getString("color");
                        String sizes = obj.getString("sizes");
                        String description = obj.getString("description");
                        product = new DummyContent.DummyItem(pid, name, cat, price, colors, sizes, description);
                        products.add(product);
                    }
                }
                if(products.isEmpty())
                    throw new FileNotFoundException("File empty for formal trouser");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                product = new DummyContent.DummyItem("p_01", "No Data Found", "Please wait for refresh", "", "", "", "No data found");
                products.add(product);
                return products;
            } catch (IOException e) {
                e.printStackTrace();
                product = new DummyContent.DummyItem("p_01", "No Data Found", "Please wait for refresh", "",  "", "", "No data found");
                products.add(product);
                return products;
            } catch (JSONException e) {
                e.printStackTrace();
                product = new DummyContent.DummyItem("p_01", "No Data Found", "Please wait for refresh", "",  "", "", "No data found");
                products.add(product);
                return products;
            }
        }
        return products;
    }

    public static List<DummyContent.DummyItem> addCategory2() {
        ArrayList<DummyContent.DummyItem> products = new ArrayList<>();
        DummyContent.DummyItem product;
        File fPath = MainActivity.mContext.getExternalFilesDir(null);
        File dataFile = new File(fPath,"json_data.json");
        if(!dataFile.exists()){
            product = new DummyContent.DummyItem("p_01", "No Data Found", "Please wait for refresh", "",  "", "", "No data found");
            products.add(product);
            return products;
        }
        else{
            try {
                FileInputStream fis = new FileInputStream(dataFile);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                StringBuilder buffer=new StringBuilder("");
                String line;
                while((line=br.readLine())!=null){
                    buffer.append(line);
                }
                JSONObject root = new JSONObject(buffer.toString());
                JSONArray productsJson = root.getJSONArray("products");
                int i=0;
                for(i=0;i<productsJson.length();i++){
                    JSONObject obj = (JSONObject) productsJson.get(i);
                    String cat = obj.getString("type");
                    if(cat.equalsIgnoreCase("cotton trouser")) {
                        String pid = obj.getString("pid");
                        String name = obj.getString("name");
                        String price = obj.getString("price");
                        String colors = obj.getString("color");
                        String sizes = obj.getString("sizes");
                        String description = obj.getString("description");
                        product = new DummyContent.DummyItem(pid, name, cat, price, colors, sizes, description);
                        products.add(product);
                    }
                }
                if(products.isEmpty())
                    throw new FileNotFoundException("File empty for cotton trouser");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                product = new DummyContent.DummyItem("p_01", "No Data Found", "Please wait for refresh", "", "", "", "No data found");
                products.add(product);
                return products;
            } catch (IOException e) {
                e.printStackTrace();
                product = new DummyContent.DummyItem("p_01", "No Data Found", "Please wait for refresh", "",  "", "", "No data found");
                products.add(product);
                return products;
            } catch (JSONException e) {
                e.printStackTrace();
                product = new DummyContent.DummyItem("p_01", "No Data Found", "Please wait for refresh", "",  "", "", "No data found");
                products.add(product);
                return products;
            }
        }
        return products;
    }

    public static List<DummyContent.DummyItem> addCategory3() {
        ArrayList<DummyContent.DummyItem> products = new ArrayList<>();
        DummyContent.DummyItem product;
        File fPath = MainActivity.mContext.getExternalFilesDir(null);
        File dataFile = new File(fPath,"json_data.json");
        if(!dataFile.exists()){
            product = new DummyContent.DummyItem("p_01", "No Data Found", "Please wait for refresh", "",  "", "", "No data found");
            products.add(product);
            return products;
        }
        else{
            try {
                FileInputStream fis = new FileInputStream(dataFile);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                StringBuilder buffer=new StringBuilder("");
                String line;
                while((line=br.readLine())!=null){
                    buffer.append(line);
                }
                JSONObject root = new JSONObject(buffer.toString());
                JSONArray productsJson = root.getJSONArray("products");
                int i=0;
                for(i=0;i<productsJson.length();i++){
                    JSONObject obj = (JSONObject) productsJson.get(i);
                    String cat = obj.getString("type");
                    if(cat.equalsIgnoreCase("corduroys")) {
                        String pid = obj.getString("pid");
                        String name = obj.getString("name");
                        String price = obj.getString("price");
                        String colors = obj.getString("color");
                        String sizes = obj.getString("sizes");
                        String description = obj.getString("description");
                        product = new DummyContent.DummyItem(pid, name, cat, price, colors, sizes, description);
                        products.add(product);
                    }
                }
                if(products.isEmpty())
                    throw new FileNotFoundException("File empty for corduroys");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                product = new DummyContent.DummyItem("p_01", "No Data Found", "Please wait for refresh", "", "", "", "No data found");
                products.add(product);
                return products;
            } catch (IOException e) {
                e.printStackTrace();
                product = new DummyContent.DummyItem("p_01", "No Data Found", "Please wait for refresh", "",  "", "", "No data found");
                products.add(product);
                return products;
            } catch (JSONException e) {
                e.printStackTrace();
                product = new DummyContent.DummyItem("p_01", "No Data Found", "Please wait for refresh", "",  "", "", "No data found");
                products.add(product);
                return products;
            }
        }
        return products;
    }

    public static List<DummyContent.DummyItem> addCategory4() {
        ArrayList<DummyContent.DummyItem> products = new ArrayList<>();
        DummyContent.DummyItem product;
        File fPath = MainActivity.mContext.getExternalFilesDir(null);
        File dataFile = new File(fPath,"json_data.json");
        if(!dataFile.exists()){
            product = new DummyContent.DummyItem("p_01", "No Data Found", "Please wait for refresh", "",  "", "", "No data found");
            products.add(product);
            return products;
        }
        else{
            try {
                FileInputStream fis = new FileInputStream(dataFile);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                StringBuilder buffer=new StringBuilder("");
                String line;
                while((line=br.readLine())!=null){
                    buffer.append(line);
                }
                JSONObject root = new JSONObject(buffer.toString());
                JSONArray productsJson = root.getJSONArray("products");
                int i=0;
                for(i=0;i<productsJson.length();i++){
                    JSONObject obj = (JSONObject) productsJson.get(i);
                    String cat = obj.getString("type");
                    if(cat.equalsIgnoreCase("jeans")) {
                        String pid = obj.getString("pid");
                        String name = obj.getString("name");
                        String price = obj.getString("price");
                        String colors = obj.getString("color");
                        String sizes = obj.getString("sizes");
                        String description = obj.getString("description");
                        product = new DummyContent.DummyItem(pid, name, cat, price, colors, sizes, description);
                        products.add(product);
                    }
                }
                if(products.isEmpty())
                    throw new FileNotFoundException("File empty for jeans");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                product = new DummyContent.DummyItem("p_01", "No Data Found", "Please wait for refresh", "", "", "", "No data found");
                products.add(product);
                return products;
            } catch (IOException e) {
                e.printStackTrace();
                product = new DummyContent.DummyItem("p_01", "No Data Found", "Please wait for refresh", "",  "", "", "No data found");
                products.add(product);
                return products;
            } catch (JSONException e) {
                e.printStackTrace();
                product = new DummyContent.DummyItem("p_01", "No Data Found", "Please wait for refresh", "",  "", "", "No data found");
                products.add(product);
                return products;
            }
        }
        return products;
    }
}
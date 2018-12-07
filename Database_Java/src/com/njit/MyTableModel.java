package com.njit;
import java.util.ArrayList;
import javax.swing.table.*;

public class MyTableModel extends DefaultTableModel{
    private ArrayList <Integer> editedIndex = new ArrayList <Integer>();

    public MyTableModel(){
        super();
    }

    //设置表格模型第一列不能被修改
    public boolean isCellEditabel(int row,int column){
        if(column==0){
            return false;
        }
        else
            return true;
    }

    //表格模型数据修改后，记录其行号
    public void setValueAt(Object aValue,int row,int column){
        super.setValueAt(aValue, row, column);
        int i,count = editedIndex.size();
        if(count==0)
            editedIndex.add(row);
        else{
            for(i=0;i<count;i++){
                if(editedIndex.get(i).intValue()>row){
                    editedIndex.add(i+1,row);
                    break;
                }
            }
            if(i>=count)
                editedIndex.add(row);
        }
    }

    //自定义方法，获取表格模型修改的数据行号列表
    public ArrayList <Integer> getEditedINdex(){
        return editedIndex;
    }
}

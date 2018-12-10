package com.njit;
import db.db;
import entity.PersonEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class personselect extends JFrame{
    private JTable table;
    private MyTableModel tablemodel;
    private JButton b1;
    private JButton b2;
    private JButton b3;
    private JToolBar tool;

    public personselect(){
        this.setSize(600,300);
        this.setTitle("员工信息");
        this.setLocationRelativeTo(getOwner());

        //获取表格模型，创建组件JTable
        tablemodel = getModel();
        table = new JTable(tablemodel);
        table.setPreferredScrollableViewportSize(new Dimension(500,250));
        JScrollPane scroll = new JScrollPane(table);
        getContentPane().add(scroll, BorderLayout.CENTER);

        //添加按钮
        b1 = new JButton("添加");
        b1.setToolTipText("添加");
        b1.setFocusable(false);
        b1.setHorizontalTextPosition(SwingConstants.CENTER);
        b1.setVerticalTextPosition(SwingConstants.BOTTOM);

        b2 = new JButton("修改");
        b2.setToolTipText("修改");
        b2.setFocusable(false);
        b2.setHorizontalTextPosition(SwingConstants.CENTER);
        b2.setVerticalTextPosition(SwingConstants.BOTTOM);

        b3 = new JButton("删除");
        b3.setToolTipText("删除");
        b3.setFocusable(false);
        b3.setHorizontalTextPosition(SwingConstants.CENTER);
        b3.setVerticalTextPosition(SwingConstants.BOTTOM);

        //创建工具栏，添加按钮
        tool = new JToolBar();
        tool.add(b1);
        tool.add(b2);
        tool.add(b3);
        tool.setRollover(true);

        //添加工具栏
        getContentPane().add(tool,BorderLayout.NORTH);

        //"添加"事件监听器
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                personadd padd = new personadd();
                padd.setVisible(true);
                dispose();
            }
        });

        //修改操作
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int i,index = 0,count;
                db dbcon = new db();
                if(table.getCellEditor()!=null){
                    table.getCellEditor().stopCellEditing();
                }
                try {
                    String sql = "update person set name=?,sex=?,birthday=?,professor=?,deptno=? where no=?";
                    PreparedStatement presta = dbcon.PreparedStatement(sql);
                    //获得JTable中所获得的行数
                    count = tablemodel.getEditedINdex().size();
                    //获得JTable中所修改的行数据，更新数据库
                    if(count>0){
                        for (i=0;i<count;i++){
                            index = tablemodel.getEditedINdex().get(i);
                            presta.setString(1,table.getValueAt(index,1).toString());
                            presta.setString(2,table.getValueAt(index,2).toString());
                            presta.setString(3,table.getValueAt(index,3).toString());
                            presta.setString(4,table.getValueAt(index,4).toString());
                            presta.setString(5,table.getValueAt(index,5).toString());
                            presta.setString(6,table.getValueAt(index,0).toString());

                            presta.addBatch();
                        }
                    }
                    presta.executeBatch();
                }
                catch (SQLException sqle){
                    System.out.println(sqle.toString());
                }
            }
        });

        //删除操作
        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                db dbcon = new db();
                try {
                    if(table.getSelectedRows().length>0){
                        //获得JTable中选中行的序列
                        int[] selRowIndexs = table.getSelectedRows();
                        java.sql.PreparedStatement presta = dbcon.PreparedStatement("delete from person where no=?");
                        for(int i=0;i<selRowIndexs.length;i++){
                            presta.setString(1,table.getValueAt(selRowIndexs[i],0).toString());
                            presta.addBatch();
                        }
                        //删除数据库中的相应记录
                        presta.executeBatch();
                        //重新加载数据到JTable
                        tablemodel = getModel();
                        table.setModel(tablemodel);
                    }
                }
                catch(SQLException sqle){
                    System.out.println(sqle.toString());
                }
            }
        });
    }

    private MyTableModel getModel() {
        //实例化表格模型类
        MyTableModel tableModel = new MyTableModel();

        db dbcon;
        try {
            //链接数据库查询数据
            dbcon = new db();
            ResultSet rs = dbcon.excuteQuery("select * from person");
            //获取查询结果中的列名，填充表格模型列
            ResultSetMetaData rsmd = rs.getMetaData();
            int Colnum = rsmd.getColumnCount();
            int i;
            for(i=1;i<=Colnum;i++)
                tableModel.addColumn(rsmd.getColumnName(i));

            //获取查询结果中的元组，填充表格模型行
            ArrayList<PersonEntity> v = new ArrayList<PersonEntity>();
            while(rs.next()) {
                PersonEntity person = new PersonEntity();
                person.setNo(rs.getString("no"));
                person.setName(rs.getString("name"));
                person.setSex(rs.getString("sex"));
                person.setBirthday(rs.getDate("birthday"));
                person.setProfessor(rs.getString("professor"));
                person.setDeptno(rs.getString("deptno"));
                v.add(person);
            }
            rs.close();
            for(i=0;i<v.size();i++) {
                tableModel.addRow(new Object[] {
                        v.get(i).getNo(),
                        v.get(i).getName(),
                        v.get(i).getSex(),
                        v.get(i).getBirthday(),
                        v.get(i).getProfessor(),
                        v.get(i).getDeptno(),
                });
            }
            dbcon.closeConn();
        }
        catch(SQLException sqle) {
            System.out.println(sqle.toString());
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return tableModel;
    }

    public static void main(String[] args) {
        try {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            e.printStackTrace();
        }
        personselect w = new personselect();
        w.setVisible(true);
        w.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}

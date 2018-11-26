/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viajes;

import com.mysql.jdbc.Connection;
import conexion.conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

/**
 *
 * @author Bmarc
 */
public class InterfaceViajes extends javax.swing.JInternalFrame {

    /**
     * Creates new form InterfaceViajes
     */
    DefaultTableModel model;
    public InterfaceViajes(String dato) {
        initComponents();
        jtxtPlacaViaje.setText(dato);
        cargarComboCiudad();
        nomCiudadDestino("");
        nomCiudadOrigen("");
        cargarTablaViajes();
        cargarModificar();
        datosTablacargar(2, jcbxOrigen);
        datosTablacargar(3, jcbxDestino); 
        
        desactivarBotonesentrar();
       
    }
    
    
    public void desactivarBotones(){
        
        btnGuardar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnBorrar.setEnabled(false);
        btnSalir.setEnabled(true);
       // jbtnViaje.setEnabled(false);
    }
     public void desactivarBotonesentrar(){
        
        btnGuardar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnBorrar.setEnabled(false);
        btnSalir.setEnabled(true);
       // jbtnViaje.setEnabled(false);
    }
   
      public void activarBotones(){
        
        btnGuardar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnCancelar.setEnabled(true);
        btnBorrar.setEnabled(false);
        btnSalir.setEnabled(true);
        //jbtnViaje.setEnabled(true);
    }
  
      
      public void actualizarBotones(){
        //btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnActualizar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnBorrar.setEnabled(true);
        btnSalir.setEnabled(true);  
        //jbtnViaje.setEnabled(true);
      }

       public void datosTablacargar(int col, JComboBox combo){
         JComboBox jcb = new JComboBox(combo.getModel());
        TableColumn tc = jtblViajes.getColumnModel().getColumn(col);
        TableCellEditor tce = new DefaultCellEditor(jcb);
        tc.setCellEditor(tce);
    }
    
        public void activarTextos(){
        jtxtPlacaViaje.setEnabled(true);
        jcbxOrigen.setEnabled(true);
        jcbxDestino.setEnabled(true);
        jdateSalida.setEnabled(true);
        
            jdateLllegada.setEnabled(true);
        jspnKilometros.setEnabled(true);
        jspnPasajeros.setEnabled(true);
        jtxtDescripcion.setEnabled(true);
        jtxtCosto.setEnabled(true);    
        
    }
    
    public void limpiarTextoViajes(){
        jcbxOrigen.getSelectedIndex();
        jcbxDestino.getSelectedIndex();
        
        
    }
    
     public void desactivarTextos(){
        jtxtPlacaViaje.setEnabled(false);
        jcbxOrigen.setEnabled(false);
        jcbxDestino.setEnabled(false);
        jdateSalida.setEnabled(false);
        jdateLllegada.setEnabled(false);
        jspnKilometros.setEnabled(false);
        jspnPasajeros.setEnabled(false);
        jtxtDescripcion.setEnabled(false);
        jtxtCosto.setEnabled(false);
        
               
        
    }
    public void cargarComboCiudad() {
        
           jcbxOrigen.removeAllItems();
           jcbxOrigen.addItem("Seleccione...");
           jcbxDestino.removeAllItems();
           jcbxDestino.addItem("Seleccione...");
        
        String sql = "";
        sql="select ciudades.CIU_CODIGO,ciudades.CIU_NOMBRE from ciudades ";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("ciudades.CIU_CODIGO");
                //String nombreMarca = rs.getString("MAR_CODIGO");
                String nombreOrigen = rs.getString("ciudades.CIU_NOMBRE");

                jcbxOrigen.addItem(nombreOrigen);
                jcbxDestino.addItem(nombreOrigen);

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
        

    }
    
    
    public String idCiudadOrigen() {
        String idciudad1 = "";
        String sql = "select CIU_CODIGO from CIUDADES where CIU_NOMBRE='" + jcbxOrigen.getSelectedItem() + "'";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {

                idciudad1 = rs.getString("CIU_CODIGO");

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return idciudad1;
    }

    public String idCiudadDestino() {
        String idciudad2 = "";
        String sql = "select CIU_CODIGO from CIUDADES where CIU_NOMBRE='" + jcbxDestino.getSelectedItem() + "'";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {

                idciudad2 = rs.getString("CIU_CODIGO");

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return idciudad2;
    }
    
    
    public String nomCiudadOrigen(String dato1) {
        String idciudad2 = "";
        String sql = "SELECT ciudades.CIU_NOMBRE FROM ciudades, viajes,autos WHERE viajes.CIU_CODIGO = ciudades.CIU_CODIGO AND viajes.AUT_PLACA=autos.AUT_PLACA AND AUTOS.AUT_PLACA LIKE'%" +dato1+"%' ";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {

                idciudad2 = rs.getString("CIU_NOMBRE");

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex+"CiudadOrigen");
        }
         System.out.println(idciudad2);
        return idciudad2;
       
    }
    
      
    public String nomCiudadDestino(String dato2) {
        String idciudad1 = "";
        String sql = "SELECT ciudades.CIU_NOMBRE FROM ciudades, viajes,autos WHERE viajes.CIU_CIU_CODIGO = ciudades.CIU_CODIGO AND AUTOS.AUT_PLACA LIKE'%" +dato2+"%' ";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {

                idciudad1 = rs.getString("CIU_NOMBRE");

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex+"CiudadDestino");
        }
      
        return idciudad1;
    }
    
    public String devolverCodigoTablaCIU(String dato) {

        String idciudad1 = "";
        String sql = "select CIU_CODIGO from CIUDADES where CIU_NOMBRE='" + dato+ "'";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {

                idciudad1 = rs.getString("CIU_CODIGO");

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return idciudad1;
    }
     public String devolverCodigoTablaCIU_CIU(String dato) {

        String CIU_CIU_COD = "", sql = "";
        try {
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            sql = "SELECT CIU_CODIGO FROM CIUDADES WHERE CIU_NOMBRE='" + dato + "'";
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                CIU_CIU_COD = rs.getString("CIU_CODIGO");
            }
        } catch (SQLException ex) {
            Logger.getLogger(InterfaceAuto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return CIU_CIU_COD;
    }
    
    
    public void guardarViaje(){
        String sql,FECHA_SALIDA,  FECHA_LLEGADA;
          sql =" INSERT INTO VIAJES(CIU_CODIGO,AUT_PLACA,CIU_CIU_CODIGO,VIA_FECHASALIDA,VIA_FECHALLEGADA,VIA_COSTO,VIA_KM,VIA_PASAJEROSBORDO,VIA_DESCRIPCION,VIA_ESTADO) VALUES(?,?,?,?,?,?,?,?,?,?)";
        
          if (jcbxOrigen.getSelectedItem()== "Seleccione...") {
            JOptionPane.showMessageDialog(this, "Ingrese la Ciudad de Origen");
            jcbxOrigen.requestFocus();
        }else if (jcbxDestino.getSelectedItem()== "Seleccione...") {
            JOptionPane.showMessageDialog(this, "Seleccione la Ciudad de Destino");
        }else if (jdateSalida.getDate()== null) {
            JOptionPane.showMessageDialog(this, "Seleccione la Fecha de Salida");
            
        }else if (jdateLllegada.getDate()== null) {
            JOptionPane.showMessageDialog(this, "Seleccione la Fecha de Llegada");
            
        }else if (jtxtCosto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el Costo");
            
        }else if (jspnKilometros.getValue().toString().equals("0")) {
            JOptionPane.showMessageDialog(this, "Ingrese el Kilometraje Mayor que 0");
            
        }else if (jspnPasajeros.getValue().toString().equals("0")) {
            JOptionPane.showMessageDialog(this, "Elija la Capacidad mayor a 0");
                         
              
            
            
        }else{
            
        
          
          
          
          
          
          try {
            PreparedStatement psd = cn.prepareStatement(sql);
            
            Date salida = jdateSalida.getDate();
                        int anio = Integer.valueOf(salida.getYear()) + 1900;
                        String mes = String.valueOf(salida.getMonth() + 1);
                        String dia = String.valueOf(salida.getDate());
            
            FECHA_SALIDA = anio + "-" + mes + "-" + dia;
            
            Date llegada= jdateLllegada.getDate();
            int anioll = Integer.valueOf(salida.getYear()) + 1900;
                        String mesll = String.valueOf(salida.getMonth() + 1);
                        String diall = String.valueOf(salida.getDate());
                        
            
            FECHA_LLEGADA = anioll + "-" + mesll + "-" + diall;
            
            
            
            psd.setString(2, jtxtPlacaViaje.getText());
            psd.setString(1, String.valueOf(idCiudadOrigen()));
            psd.setString(3, String.valueOf(idCiudadDestino()));
            //if (jcbxColor.getSelectedItem()=="Seleccione...") {
            psd.setString(4, FECHA_SALIDA);    
            psd.setString(5, FECHA_LLEGADA);
            psd.setString(6, jtxtCosto.getText());
            psd.setString(7, jspnKilometros.getValue().toString());
            psd.setString(8, jspnPasajeros.getValue().toString());
        if (jtxtDescripcion.getText().isEmpty()) {
            
            psd.setString(9, "NADA");
        }else{
            psd.setString(9, jtxtDescripcion.getText());
        }         
            psd.setString(10, "1");
            psd.executeUpdate();
            JOptionPane.showMessageDialog(null, "Se inserto correctamente el Viaje");
             limpiarTextoViajes();
          desactivarBotones();
//            desactivarTextos();
cargarTablaViajes();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        }
        
    }
     public String obtenerCiudad(String CIU_COD) {
        String sql;
        String nombre = null;
        
        sql = "SELECT CIU_NOMBRE " + "FROM CIUDADES " + "WHERE CIU_CODIGO ='" + CIU_COD + "'";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                nombre = rs.getString("CIU_NOMBRE");
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
        return nombre;
    }
    
    public void cargarTablaViajes(){
        String origen =nomCiudadOrigen("");
        String destino =nomCiudadDestino("");
        try {
            String[] titulos={"CODIGO","PLACA", "ORIGEN", "DESTINO", "SALIDA", "LLEGADA", "COSTO", "KILOMETRAJE","CAPACIDAD", "OBSERVACION"};
            model = new DefaultTableModel(null, titulos);
            jtblViajes.setModel(model);
            String[] datos = new String[10];
            
            String sql;
            //sql= " SELECT * FROM VIAJES WHERE AUT_PLACA= '"+ jtxtPlacaViaje.getText()+"'";
            sql= " SELECT * FROM VIAJES WHERE VIA_ESTADO = '1'";
            //sql= " SELECT VIAJES.VIA_CODIGO,VIAJES.AUT_PLACA,VIAJES.VIA_FECHASALIDA, VIAJES.VIA_FECHALLEGADA, VIAJES.VIA_COSTO, VIAJES.VIA_KM, VIAJES.VIA_PASAJEROSBORDO,VIAJES.VIA_DESCRIPCION"
            //        + " FROM VIAJES";
            
            Statement psd=cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while(rs.next()){
                datos[0]=rs.getString("VIA_CODIGO");
                datos[1]=rs.getString("AUT_PLACA");
                datos[2]=obtenerCiudad(rs.getString("CIU_CODIGO"));
                datos[3]=obtenerCiudad(rs.getString("CIU_CIU_CODIGO"));
                datos[4]=rs.getString("VIA_FECHASALIDA");
                datos[5]=rs.getString("VIA_FECHALLEGADA");
                datos[6]=rs.getString("VIA_COSTO");
                datos[7]=rs.getString("VIA_KM");
                datos[8]=rs.getString("VIA_PASAJEROSBORDO");
                datos[9]=rs.getString("VIA_DESCRIPCION");
                model.addRow(datos);
            }
            jtblViajes.setModel(model);
            
           
                
           model.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent tme) {
                    if (tme.getType() == TableModelEvent.UPDATE) {
                        int columna = tme.getColumn();
                        int fila = tme.getFirstRow();
                        String colname = null;
                        if (columna == 2) {
                        colname="CIU_CODIGO";   
                        }else if (columna == 3) {
                        colname="CIU_CIU_CODIGO";   
                        }else if (columna == 4) {
                        colname="VIA_FECHASALIDA";   
                        }else if(columna == 5){
                        colname="VIA_FECHALLEGADA";
                        }else if(columna == 6){
                            colname="VIA_COSTO";
                        }else if(columna == 7){
                            colname="VIA_KM";
                        } else if(columna == 8){
                            colname="VIA_PASAJEROSBORDO";
                        }else if(columna == 9){
                            colname="VIA_DESCRIPCION";
                        }
                        
                        String f;
                        f = model.getValueAt(tme.getFirstRow(), tme.getColumn()).toString();
                        if (colname.equals("CIU_CODIGO")) {
                            f = devolverCodigoTablaCIU(model.getValueAt(tme.getFirstRow(), tme.getColumn()).toString());
                       // JOptionPane.showMessageDialog(null, f);
                        }else if (colname.equals("CIU_CIU_CODIGO")) {
                            f = devolverCodigoTablaCIU_CIU(model.getValueAt(tme.getFirstRow(), tme.getColumn()).toString());
                        }
                        
                       // String sql=" UPDATE VIAJES SET "+colname+" = '"+ jtblViajes.getValueAt(fila, columna)+"' WHERE AUT_PLACA ='"+jtblViajes.getValueAt(fila, 1)+"' AND VIA_CODIGO='"+jtblViajes.getValueAt(fila, 0)+"'";                   
                                  String sql = ("UPDATE VIAJES SET " + colname + "='" + f + "' WHERE AUT_PLACA='" + model.getValueAt(tme.getFirstRow(), 1) + "'AND  VIA_CODIGO='"+model.getValueAt(tme.getFirstRow(), 0)+"' ");
                        PreparedStatement psd;
                            try {
                                psd = cn.prepareStatement(sql);
                                psd.executeUpdate();
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, ex+"No valio");
                            }
                   desactivarTextos();
            limpiarTextoViajes();
           desactivarBotones();
                       
                       
                        
                    }
                   
                }
                
                
            });
            
            jtblViajes.setModel(model);
            
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex+"Cargar tblViajes");
        }
    
        
        
        
        
    }
    
   
    
    
//    public void CargarTablaViajes() {
//        
//        String[] titulos = {"CODIGO", "ORIGEN", "DESTINO", "FECHA SALIDA", "FECHA LLEGADA", "HORA", "COSTO", "KM", "OBSERVACION"};
//        modelo = new DefaultTableModel(null, titulos);
//        
//        String[] registros = new String[9];
//        Conexion cc = new Conexion();
//        Connection cn = cc.conectar();
//        
//        String sql = "";
//        
//        sql = "SELECT * FROM viajes WHERE AUT_PLACA='" + txtPlaca.getText() + "' ";
//        try {
//            Statement psd = cn.createStatement();
//            ResultSet rs = psd.executeQuery(sql);
//            while (rs.next()) {
//                registros[0] = String.valueOf(rs.getInt("VIA_CODIGO"));
//                registros[1] = obtenerCiudad(rs.getString("ORIDES_CODIGO"));
//                registros[2] = obtenerCiudad(rs.getString("ORI_ORIDES_CODIGO"));
//                registros[3] = rs.getString("VIA_FECHA_SALIDA");
//                registros[4] = rs.getString("VIA_FECHA_LLEGADA");
//                registros[5] = rs.getString("VIA_HORA");
//                registros[6] = rs.getString("VIA_COSTO");
//                registros[7] = rs.getString("VIA_KM");
//                registros[8] = rs.getString("VIA_OBSERVACION");
//                
//                modelo.addRow(registros);
//            }
//            
//            tblViajes.setModel(modelo);
//            
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, ex);
//        }
////        tblViajes.setModel(modelo);
//    }
//    
    
       public void cargarModificar() {
        jtxtCodigo.setEnabled(false);
        jtblViajes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (jtblViajes.getSelectedRow() != -1) {
                    
                      activarTextos();
                       actualizarBotones();
                    int fila = jtblViajes.getSelectedRow();
                    jtxtCodigo.setText(jtblViajes.getValueAt(fila, 0).toString());
                    

                    
                   jtxtPlacaViaje.setText(jtblViajes.getValueAt(fila, 1).toString().trim());
                    jcbxOrigen.setSelectedItem(jtblViajes.getValueAt(fila, 2).toString().trim());
                    
                    jcbxDestino.setSelectedItem(jtblViajes.getValueAt(fila, 3).toString());
                   
                    String[] salida = new String[1];
                    salida = jtblViajes.getValueAt(fila, 4).toString().split(" ");
                    String fechaSalida = salida[0];
                    SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy-MM-dd");
                     Date fechS = null;
                    try {
                        fechS = formatoDeFecha.parse(fechaSalida);
                        //System.out.println(fechS.toString());
                        jdateSalida.setDate(fechS);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                   
                    String[] llegada = new String[1];
                    llegada = jtblViajes.getValueAt(fila, 5).toString().split(" ");
                    String fechaLlegada = llegada[0];
                    SimpleDateFormat formatoDeFechaLlegada = new SimpleDateFormat("yyyy-MM-dd");
                     Date fechLl = null;
                    try {
                        fechLl = formatoDeFechaLlegada.parse(fechaLlegada);
                        //System.out.println(fechS.toString());
                        jdateLllegada.setDate(fechLl);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                    
                    
                    
                    jtxtCosto.setText(jtblViajes.getValueAt(fila, 6).toString().trim());
                    jspnKilometros.setValue(Integer.valueOf(jtblViajes.getValueAt(fila, 7).toString().trim()));
                    jspnPasajeros.setValue(Integer.valueOf(jtblViajes.getValueAt(fila, 8).toString().trim()));
                    jtxtDescripcion.setText(jtblViajes.getValueAt(fila, 9).toString().trim());
                    
                    btnCancelar.setEnabled(true);
                }
            }
        });
    }
       
    public void modificarViaje(){
        
        
          
        
//          if (jcbxOrigen.getSelectedItem()== "Seleccione...") {
//            JOptionPane.showMessageDialog(this, "Ingrese la Ciudad de Origen");
//            jcbxOrigen.requestFocus();
//        }else if (jcbxDestino.getSelectedItem()== "Seleccione...") {
//            JOptionPane.showMessageDialog(this, "Seleccione la Ciudad de Destino");
//        }else if (jdateSalida.getDate()== null) {
//            JOptionPane.showMessageDialog(this, "Seleccione la Fecha de Salida");
//            
//        }else if (jdateLllegada.getDate()== null) {
//            JOptionPane.showMessageDialog(this, "Seleccione la Fecha de Llegada");
//            
//        }else if (jtxtCosto.getText().isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Ingrese el Costo");
//            
//        }else if (jspnKilometros.getValue().toString().equals("0")) {
//            JOptionPane.showMessageDialog(this, "Ingrese el Kilometraje Mayor que 0");
//            
//        }else if (jspnPasajeros.getValue().toString().equals("0")) {
//            JOptionPane.showMessageDialog(this, "Elija la Capacidad mayor a 0");
//                         
//              
//            
//            
//        }else{
            
        
            
        String sql;
       
        Date salida = jdateSalida.getDate();
            int anio = Integer.valueOf(salida.getYear()) + 1900;
            String mes = String.valueOf(salida.getMonth() + 1);
            String fecha = String.valueOf(salida.getDate());
            String sal = anio + "-" + mes + "-" + fecha   ;
            Date llegada = jdateLllegada.getDate();
            
            int anio2 = Integer.valueOf(llegada.getYear()) + 1900;
            String mes2 = String.valueOf(llegada.getMonth() + 1);
            String fecha2 = String.valueOf(llegada.getDate());
            String lle = anio2 + "-" + mes2 + "-" + fecha2;
        
        
        
        
        sql="UPDATE VIAJES SET CIU_CODIGO= '"+ idCiudadOrigen()+"',"
                + "CIU_CIU_CODIGO ='"+idCiudadDestino()+"',"
                + "VIA_FECHASALIDA ='"+sal+"',"
                + " VIA_FECHALLEGADA ='"+lle+"',"
                + "VIA_COSTO ='"+jtxtCosto.getText() +"',"
                + "VIA_KM='" + jspnKilometros.getValue() + "',"
                + "VIA_PASAJEROSBORDO='"+jspnPasajeros.getValue()+"',"
                + "VIA_DESCRIPCION='"+jtxtDescripcion.getText()+"'"
                + "WHERE VIA_CODIGO='"+jtxtCodigo.getText()+"'";
        
        try {
                PreparedStatement psd = cn.prepareStatement(sql);
                int n = psd.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Se actualizo una fila");
                    cargarTablaViajes();
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex+"No se Modifico");
            }
        
    
    }
 
       public void borrar(){
           if (JOptionPane.showConfirmDialog(new JInternalFrame(), "Estas Seguro de borrar el Registro", "Ventana borrar", JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION) {
           String sql = "UPDATE viajes  SET VIA_ESTADO= '0'where VIA_CODIGO='" + jtxtCodigo.getText() + "'";
            try {
                PreparedStatement psd = cn.prepareStatement(sql);
                int n = psd.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "La fila se elimino correctamente");
                }cargarTablaViajes();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "No se elimino el viaje");
            }
       }
       }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jtxtPlacaViaje = new javax.swing.JTextField();
        jcbxOrigen = new javax.swing.JComboBox<>();
        jcbxDestino = new javax.swing.JComboBox<>();
        jdateSalida = new com.toedter.calendar.JDateChooser();
        jdateLllegada = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jspnKilometros = new javax.swing.JSpinner();
        jspnPasajeros = new javax.swing.JSpinner();
        jtxtCosto = new componentesMios.NumerosMios();
        jtxtDescripcion = new componentesMios.textoMio();
        jLabel10 = new javax.swing.JLabel();
        jtxtCodigo = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblViajes = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel1.setText("Placa:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel2.setText("Destino:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel3.setText("Fecha Salida:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel4.setText("Origen:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel5.setText("Costo:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel6.setText("Fecha Llegada:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel7.setText("Kilometros:");

        jtxtPlacaViaje.setEditable(false);

        jdateSalida.setDateFormatString("yyyy-MM-d");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel8.setText("Pasajeros:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel9.setText("Descripcion:");

        jspnKilometros.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        jspnPasajeros.setModel(new javax.swing.SpinnerNumberModel(0, 0, 5, 1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("$");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addGap(91, 91, 91))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(84, 84, 84)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(104, 104, 104)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jtxtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 29, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbxDestino, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jcbxOrigen, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jdateSalida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jdateLllegada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jtxtPlacaViaje, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(84, 84, 84))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jspnKilometros, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                            .addComponent(jspnPasajeros)
                            .addComponent(jtxtCosto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jtxtPlacaViaje, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jcbxOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jcbxDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jdateSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jdateLllegada, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jtxtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jspnKilometros, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jspnPasajeros, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jtblViajes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jtblViajes);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(142, 142, 142))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnGuardar.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/save.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnActualizar.setBackground(new java.awt.Color(255, 255, 255));
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/refresh.png"))); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(255, 255, 255));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnBorrar.setBackground(new java.awt.Color(255, 255, 255));
        btnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/delete.png"))); // NOI18N
        btnBorrar.setText("Borrar");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/exit.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBorrar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnGuardar)
                .addGap(32, 32, 32)
                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btnBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalir)
                .addGap(41, 41, 41))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        guardarViaje();
        //desactivarTextos();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
//        modificar();
    modificarViaje();
    desactivarTextos();
    desactivarBotones();
    
        // TODO add your handling code here:
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
           limpiarTextoViajes();
     desactivarTextos();
         desactivarBotones();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        // TODO add your handling code here:
       borrar();
        
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();

    }//GEN-LAST:event_btnSalirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InterfaceViajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfaceViajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfaceViajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfaceViajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               // new InterfaceViajes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> jcbxDestino;
    private javax.swing.JComboBox<String> jcbxOrigen;
    private com.toedter.calendar.JDateChooser jdateLllegada;
    private com.toedter.calendar.JDateChooser jdateSalida;
    private javax.swing.JSpinner jspnKilometros;
    private javax.swing.JSpinner jspnPasajeros;
    private javax.swing.JTable jtblViajes;
    private javax.swing.JLabel jtxtCodigo;
    private componentesMios.NumerosMios jtxtCosto;
    private componentesMios.textoMio jtxtDescripcion;
    private javax.swing.JTextField jtxtPlacaViaje;
    // End of variables declaration//GEN-END:variables
conexion cc = new conexion();
Connection cn = cc.conectar();
}

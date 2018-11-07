/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viajes;

import com.mysql.jdbc.Connection;
import conexion.conexion;
import java.awt.event.ItemEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Bmarc
 */
public class InterfaceAuto extends JInternalFrame {
    
    DefaultTableModel model;
    /**
     * Creates new form InterfaceAuto
     */
    public InterfaceAuto() {
        initComponents();
        cargarTablaAutos("");
        cargarColor();
        desactivarTextos();
        desactivarBotones();
        
        jcbxMarca.addItem("Seleccione...");
        cargarComboMarca();    
        limpiarTextos();
        cargarmodificar();
     
        
        
        
    }
    
    public void cargarColor(){
        jcbxColor.addItem("Seleccione...");
        jcbxColor.addItem("Rojo");
        jcbxColor.addItem("Verde");
        jcbxColor.addItem("Azul");
        jcbxColor.addItem("Negro");
        jcbxColor.addItem("Blanco");
        jcbxColor.addItem("Plateado");
    }
    
   

    
    public void activarTextos(){
        txtPlaca.setEnabled(true);
        jcbxModelo.setEnabled(true);
        txtAno.setEnabled(true);
        jcbxColor.setEnabled(true);
        spnCapacidad.setEnabled(true);
        txtDescripcion.setEnabled(true);
        jcbxMarca.setEnabled(true);
               
        
    }
    
    public void limpiarTextos(){
        txtPlaca.setText("");
        jcbxModelo.setSelectedIndex(-1);
        txtAno.setText("");
        jcbxColor.setSelectedIndex(0);
        
        spnCapacidad.setValue(0);
        txtDescripcion.setText("");
        jcbxMarca.setSelectedIndex(0);
               
        
    }
    public void desactivarTextos(){
        txtPlaca.setEnabled(false);
        jcbxModelo.setEnabled(false);
        txtAno.setEnabled(false);
        jcbxColor.setEnabled(false);
        spnCapacidad.setEnabled(false);
        txtDescripcion.setEnabled(false);
        jcbxMarca.setEnabled(false);
               
        
    }
    
    public void desactivarBotones(){
        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnBorrar.setEnabled(false);
        btnSalir.setEnabled(true);
    }
      public void activarBotones(){
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnCancelar.setEnabled(true);
        btnBorrar.setEnabled(false);
        btnSalir.setEnabled(true);
    }
      
      public void actualizarBotones(){
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnActualizar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnBorrar.setEnabled(true);
        btnSalir.setEnabled(true);  
      }
      public void guardar(){
          String sql;
          sql =" INSERT INTO AUTOS VALUES(?,?,?,?,?,?)";
          
          if (txtPlaca.getText().isEmpty()) {
              JOptionPane.showMessageDialog(this, "Ingrese Placa");
              
                    txtPlaca.requestFocus();
                      
          }else if (jcbxMarca.getSelectedItem()=="Seleccione...") {
              JOptionPane.showMessageDialog(this, "Eliga una Marca");
              jcbxMarca.requestFocus();
          
          }else if (jcbxModelo.getSelectedItem()=="Seleccione...") {
              JOptionPane.showMessageDialog(this, "Elija un Modelo");
              jcbxModelo.requestFocus();
          }else if (txtAno.getText().isEmpty()) {
              JOptionPane.showMessageDialog(this, "Ingrese el Año");
          txtAno.requestFocus();
              
          }else if (spnCapacidad.getValue().toString().equals("0")) {
              JOptionPane.showMessageDialog(this, "Elija la Capacidad mayor a O");
              spnCapacidad.requestFocus();
              
          
          }else{
{
          
        try {
            PreparedStatement psd = cn.prepareStatement(sql);
            
            psd.setString(1, txtPlaca.getText());
            psd.setString(2, String.valueOf(idmodelo()));
            psd.setString(3, txtAno.getText());
            if (jcbxColor.getSelectedItem()=="Seleccione...") {
            psd.setString(4, "SIN COLOR");    
            }else{
            psd.setString(4, jcbxColor.getSelectedItem().toString());
            }
            
            psd.setString(5, spnCapacidad.getValue().toString());
            if (txtDescripcion.getText().isEmpty()) {
                      psd.setString(6,"NADA");
            }else{
            psd.setString(6, txtDescripcion.getText());
            }
            
            
            psd.executeUpdate();
            JOptionPane.showMessageDialog(null, "Se inserto correctamente el Auto");
            limpiarTextos();
            desactivarBotones();
            desactivarTextos();
            cargarTablaAutos("");
            
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "NO VALE");
        }
}
          }
      }
      
      public String idmodelo() {
        String idmodelo1 = "";
        String sql = "select MOD_CODIGO from modelo where MOD_NOMBRE='" + jcbxModelo.getSelectedItem() + "'";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {

                idmodelo1 = rs.getString("MOD_CODIGO");

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return idmodelo1;
    }

      
      public void cargarComboMarca(){
          String sql;
          String id, idMarca, modNombre;
          sql = " SELECT * FROM Marca";
        try {
            //statement porque solo vamos a seleccionar
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            
            while (rs.next()){
                id=rs.getString(1);
              
                modNombre=rs.getString(2);
                
                jcbxMarca.addItem(modNombre);
                
            }
            
        } catch (SQLException ex) {
         JOptionPane.showMessageDialog(null, ex);

        }
      }
       public void cargarComboModelo1() {
        
           jcbxModelo.removeAllItems();
           jcbxModelo.addItem("Seleccione...");
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql = "";
        sql="select modelo.MOD_CODIGO,modelo.MOD_NOMBRE,marca.MAR_CODIGO from modelo,marca where marca.MAR_NOMBRE='"+ jcbxMarca.getSelectedItem()+ "' and marca.MAR_CODIGO=modelo.MAR_CODIGO ";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("modelo.MOD_CODIGO");
                //String nombreMarca = rs.getString("MAR_CODIGO");
                String nombreModelo = rs.getString("modelo.MOD_NOMBRE");

                jcbxModelo.addItem(nombreModelo);

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }
 
      
    
    
       public void cargarTablaAutos(String dato){
           try {
            String [] titulos={"PLACA","MARCA","MODELO","AÑO","COLOR","CAPACIDAD","DESCRIPCION"};
            String [] registros = new String[7];
            String sql;
            sql="SELECT AUTOS.AUT_PLACA, MARCA.MAR_NOMBRE, MODELO.MOD_NOMBRE, AUTOS.AUT_ANIO, AUTOS.AUT_COLOR, AUTOS.AUT_CAPACIDAD, AUTOS.AUT_DESCRIPCION "
                    + "FROM AUTOS, MARCA, MODELO "
                    + "WHERE AUTOS.MOD_CODIGO = MODELO.MOD_CODIGO AND MODELO.MAR_CODIGO = MARCA.MAR_CODIGO AND AUTOS.AUT_PLACA LIKE'%" +dato+"%' ";
            model = new DefaultTableModel(null,titulos);
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                registros[0] = rs.getString("AUT_PLACA");
                registros[1] = rs.getString("MAR_NOMBRE");
                registros[2] = rs.getString("MOD_NOMBRE");
                registros[3] = rs.getString("AUT_ANIO");
                registros[4] = rs.getString("AUT_COLOR");
                registros[5] = rs.getString("AUT_CAPACIDAD");
                registros[6] = rs.getString("AUT_DESCRIPCION");
            model.addRow(registros);
            }
                 
                              
jtblAutos.setModel(model);
                
           model.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent tme) {
                    if (tme.getType() == TableModelEvent.UPDATE) {
                        int columna = tme.getColumn();
                        int fila = tme.getFirstRow();
                        String colname = null;
                        
                        
                        if (columna == 3) {
                        colname="AUT_ANIO";   
                        }else if(columna == 4){
                        colname="AUT_COLOR";
                        }else if(columna == 5){
                            colname="AUT_CAPACIDAD";
                        }else if(columna == 6){
                            colname="AUT_DESCRIPCION";
                        }
                        
                        String sql=" UPDATE AUTOS SET "+colname+" = '"+ jtblAutos.getValueAt(fila, columna)+"' WHERE AUT_PLACA ='"+jtblAutos.getValueAt(fila, 0)+"'";                   
                PreparedStatement psd;
                            try {
                                psd = cn.prepareStatement(sql);
                                psd.executeUpdate();
                            } catch (SQLException ex) {
                                Logger.getLogger(InterfaceAuto.class.getName()).log(Level.SEVERE, null, ex);
                            }
                   desactivarTextos();
            limpiarTextos();
            desactivarBotones();
                       
                       
                        
                    }
                   
//                    else if(columna == 4){
//                        String sql=" UPDATE AUTOS SET AUT_COLOR= '"+ jtblAutos.getValueAt(fila, columna)+"' WHERE AUT_PLACA ='"+jtblAutos.getValueAt(fila, 0)+"'";
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
                
                
            });
           
           jtblAutos.setModel(model);
                 
           
           } catch (SQLException ex) {
            Logger.getLogger(InterfaceAuto.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       }
       
       public void cargarmodificar(){
           jtblAutos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
               @Override
               public void valueChanged(ListSelectionEvent lse) {
                   if(jtblAutos.getSelectedRow()!=-1){
                       activarTextos();
                       actualizarBotones();
                       int fila = jtblAutos.getSelectedRow();
                       txtPlaca.setText(jtblAutos.getValueAt(fila, 0).toString().trim());
                       jcbxMarca.setSelectedItem(jtblAutos.getValueAt(fila, 1).toString().trim());
                       jcbxModelo.setSelectedItem(jtblAutos.getValueAt(fila, 2).toString().trim());
                       txtAno.setText(jtblAutos.getValueAt(fila, 3).toString().trim());
                       jcbxColor.setSelectedItem(jtblAutos.getValueAt(fila, 4).toString().trim());
                       spnCapacidad.setValue(Integer.valueOf(jtblAutos.getValueAt(fila, 5).toString()));
                       txtDescripcion.setText(jtblAutos.getValueAt(fila, 6).toString().trim());
                       
                       txtPlaca.setEditable(false);
                   }
               }
           });
       }
       public void modificar(){
           String sql;
           sql ="UPDATE AUTOS SET MOD_CODIGO= '"+ idmodelo()+"',"
                   + "AUT_ANIO= '"+ txtAno.getText()+"',"
                   + "AUT_CAPACIDAD= '"+ spnCapacidad.getValue()+"',"
                   + "AUT_COLOR= '"+jcbxColor.getSelectedItem()+"',"
                   + "AUT_DESCRIPCION= '"+ txtDescripcion.getText()+"' "
                   + "  WHERE AUT_PLACA ='"+txtPlaca.getText() +"' ";
        try {
            PreparedStatement psd = cn.prepareStatement(sql);
            psd.executeUpdate();
            JOptionPane.showMessageDialog(this, "Se modifico Correctamente");
            cargarTablaAutos("");
            desactivarTextos();
            limpiarTextos();
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this, "No se Modifico");
            
        }
       }
       
     
       
       public void borrar(){
           if (JOptionPane.showConfirmDialog(new JInternalFrame(), "Estas Seguro de borrar el Registro", "Ventana borrar", JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION) {
               
             String sql= "DELETE FROM AUTOS WHERE AUT_PLACA = '"+txtPlaca.getText()+"' ";
        try {
            PreparedStatement psd= cn.prepareStatement(sql);
            psd.executeUpdate();
            
            
                     
            cargarTablaAutos("");
            desactivarTextos();
            limpiarTextos();
         
        } catch (SQLException ex) {
            Logger.getLogger(InterfaceAuto.class.getName()).log(Level.SEVERE, null, ex);
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
        jcbxModelo = new javax.swing.JComboBox<>();
        spnCapacidad = new javax.swing.JSpinner();
        jcbxColor = new javax.swing.JComboBox<>();
        jcbxMarca = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtPlaca = new javax.swing.JFormattedTextField();
        txtAno = new componentesMios.NumerosMios();
        txtDescripcion = new componentesMios.textoMio();
        jPanel2 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jbtnViaje = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblAutos = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txtBuscarPlaca = new javax.swing.JTextField();

        setBackground(new java.awt.Color(153, 204, 255));
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Arial Narrow", 0, 24)); // NOI18N
        jLabel1.setText("Placa");

        jLabel2.setFont(new java.awt.Font("Arial Narrow", 0, 24)); // NOI18N
        jLabel2.setText("Modelo");

        jLabel3.setFont(new java.awt.Font("Arial Narrow", 0, 24)); // NOI18N
        jLabel3.setText("Año");

        jLabel4.setFont(new java.awt.Font("Arial Narrow", 0, 24)); // NOI18N
        jLabel4.setText("Color");

        jLabel5.setFont(new java.awt.Font("Arial Narrow", 0, 24)); // NOI18N
        jLabel5.setText("Capacidad");

        jLabel6.setFont(new java.awt.Font("Arial Narrow", 0, 24)); // NOI18N
        jLabel6.setText("Descripción");

        jcbxModelo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbxModeloItemStateChanged(evt);
            }
        });
        jcbxModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbxModeloActionPerformed(evt);
            }
        });

        spnCapacidad.setModel(new javax.swing.SpinnerNumberModel(0, 0, 5, 1));

        jcbxMarca.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbxMarcaItemStateChanged(evt);
            }
        });
        jcbxMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbxMarcaActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial Narrow", 0, 24)); // NOI18N
        jLabel7.setText("Marca");

        try {
            txtPlaca.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("UUU-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        txtDescripcion.setText("textoMio1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(67, 67, 67))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(74, 74, 74)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jcbxMarca, 0, 154, Short.MAX_VALUE)
                                    .addComponent(txtPlaca))))
                        .addContainerGap(142, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbxModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcbxColor, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnCapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(81, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jcbxMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jcbxModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnCapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbxColor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnNuevo.setBackground(new java.awt.Color(255, 255, 255));
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/new.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

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

        jbtnViaje.setText("Viaje");
        jbtnViaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnViajeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jbtnViaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBorrar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(49, 49, 49))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGuardar)
                .addGap(18, 18, 18)
                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalir)
                .addGap(18, 18, 18)
                .addComponent(jbtnViaje, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPanel3KeyReleased(evt);
            }
        });

        jtblAutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jtblAutos);

        jLabel8.setText("Buscar por Placa: ");

        txtBuscarPlaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarPlacaActionPerformed(evt);
            }
        });
        txtBuscarPlaca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarPlacaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 696, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel8)
                        .addGap(60, 60, 60)
                        .addComponent(txtBuscarPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtBuscarPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        activarBotones();
        activarTextos();
        limpiarTextos();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        limpiarTextos();
        desactivarTextos();
        desactivarBotones();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
        
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        guardar();
         
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void jcbxModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbxModeloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbxModeloActionPerformed

    private void jcbxMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbxMarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbxMarcaActionPerformed

    private void jcbxMarcaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbxMarcaItemStateChanged

        cargarComboModelo1();
        
        
    }//GEN-LAST:event_jcbxMarcaItemStateChanged

    private void jcbxModeloItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbxModeloItemStateChanged
        
    }//GEN-LAST:event_jcbxModeloItemStateChanged

    private void txtBuscarPlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarPlacaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarPlacaActionPerformed

    private void jPanel3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel3KeyReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jPanel3KeyReleased

    private void txtBuscarPlacaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarPlacaKeyReleased
cargarTablaAutos(txtBuscarPlaca.getText());
// TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarPlacaKeyReleased

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        modificar();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        // TODO add your handling code here:
        borrar();
        desactivarBotones();
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void jbtnViajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnViajeActionPerformed
        // TODO add your handling code here:
        InterfaceViajes viaje = new InterfaceViajes(txtPlaca.getText());
        
        InterfacePrincipal.jdeskprincipal.add(viaje).setVisible(true);
        //viaje.show();
        
    }//GEN-LAST:event_jbtnViajeActionPerformed

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
            java.util.logging.Logger.getLogger(InterfaceAuto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfaceAuto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfaceAuto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfaceAuto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfaceAuto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnViaje;
    private javax.swing.JComboBox<String> jcbxColor;
    private javax.swing.JComboBox<String> jcbxMarca;
    private javax.swing.JComboBox<String> jcbxModelo;
    private javax.swing.JTable jtblAutos;
    private javax.swing.JSpinner spnCapacidad;
    private componentesMios.NumerosMios txtAno;
    private javax.swing.JTextField txtBuscarPlaca;
    private componentesMios.textoMio txtDescripcion;
    private javax.swing.JFormattedTextField txtPlaca;
    // End of variables declaration//GEN-END:variables
conexion cc=new conexion();
Connection cn = cc.conectar();
}

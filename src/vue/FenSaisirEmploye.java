package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.tools.DocumentationTool;
import model.Departments;
import model.Employees;
import model.Locations;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import util.HibernateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import javax.swing.JFormattedTextField;
import model.Jobs;
import org.jdatepicker.impl.*;
import org.jdatepicker.util.*;
import org.jdatepicker.*;

/**
 *
 * @author Administrateur
 */
public class FenSaisirEmploye {

    JFrame fenetre;

    JPanel jpPrincipal;
    JPanel jpNord;
    JPanel jpFormulaire;
    JPanel jpBoutons;

    JLabel lblTitre;

    JLabel lblEmployeeId;
    JLabel lblFirstName;
    JLabel lblLastName;
    JLabel lblEmail;
    JLabel lblHireDate;
    JLabel lblJobId;
    JLabel lblJobIdDeails;

    JTextField txtEmployeeId;
    JTextField txtFirstName;
    JTextField txtLastName;
    JTextField txtEmail;
    JTextField txtHireDate;
    JTextField txtJobId;

    JButton btnEnregistrer;
    JButton btnQuitter;

    JComboBox<String> comboBoxJob = new JComboBox<String>();
    UtilDateModel modelDate;

    public FenSaisirEmploye() {

        prepareGUI();

    }

    private void prepareGUI() {

        fenetre = new JFrame();

        lblTitre = new JLabel("Nouveau employé");
        lblEmployeeId = new JLabel("Employé ID:");
        lblFirstName = new JLabel("Prenom:");
        lblLastName = new JLabel("Nom:");
        lblEmail = new JLabel("Courriel:");
        lblHireDate = new JLabel("Date d'embouche:");
        lblJobId = new JLabel("Job:");
        lblJobIdDeails = new JLabel();

        txtEmployeeId = new JTextField(10);
        txtFirstName = new JTextField(10);
        txtLastName = new JTextField(10);
        txtEmail = new JTextField(10);
        txtHireDate = new JTextField(10);
        txtJobId = new JTextField(10);

        btnEnregistrer = new JButton("Enregistrer");
        btnQuitter = new JButton("Quitter");

        jpPrincipal = new JPanel(new BorderLayout(10, 10));
        jpPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        fenetre.add(jpPrincipal);

        //panel jpNord
        jpNord = new JPanel();
        jpPrincipal.add(jpNord, BorderLayout.NORTH);
        lblTitre.setForeground(Color.WHITE);
        lblTitre.setFont(new Font(null, Font.BOLD, 16));

        jpNord.add(lblTitre);
        jpNord.setBackground(Color.GRAY);

        //panel jpFormulaire
        jpFormulaire = new JPanel(new GridLayout(7, 3, 20, 10));
        jpPrincipal.add(jpFormulaire, BorderLayout.CENTER);

        jpFormulaire.add(lblEmployeeId);
        lblEmployeeId.setHorizontalAlignment(SwingConstants.RIGHT);
        jpFormulaire.add(txtEmployeeId);
        jpFormulaire.add(new JLabel());

        jpFormulaire.add(lblFirstName);
        lblFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
        jpFormulaire.add(txtFirstName);
        jpFormulaire.add(new JLabel());

        jpFormulaire.add(lblLastName);
        lblLastName.setHorizontalAlignment(SwingConstants.RIGHT);
        jpFormulaire.add(txtLastName);
        jpFormulaire.add(new JLabel());

        jpFormulaire.add(lblEmail);
        lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
        jpFormulaire.add(txtEmail);
        jpFormulaire.add(new JLabel());

        jpFormulaire.add(lblHireDate);
        lblHireDate.setHorizontalAlignment(SwingConstants.RIGHT);
//        jpFormulaire.add(txtHireDate);

        //calendar picker
        UtilDateModel model = new UtilDateModel();
        //model.setDate(20,04,2014);
        Properties p = new Properties();
        p.put("text.today", "");
        p.put("text.month", "");
        p.put("text.year", "");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        jpFormulaire.add(datePicker);

        //fin calendar picker
        jpFormulaire.add(new JLabel());

        jpFormulaire.add(lblJobId);
        lblJobId.setHorizontalAlignment(SwingConstants.RIGHT);
        genererComboBoxJobsID();
        jpFormulaire.add(comboBoxJob);
        comboBoxJob.addItemListener(itemListener);
        jpFormulaire.add(lblJobIdDeails);

        //panel jpBoutons
        jpBoutons = new JPanel();
        jpPrincipal.add(jpBoutons, BorderLayout.SOUTH);
        jpBoutons.add(btnEnregistrer);
        jpBoutons.add(btnQuitter);

        //bouton Enregistrer
        btnEnregistrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!txtFirstName.getText().trim().equals("")
                        && !txtLastName.getText().trim().equals("")
                        && !txtEmail.getText().trim().equals("")) {

                    ajouterEmploye();
                } else {
                    JOptionPane.showMessageDialog(null, "Tous les champs sont obligatoir",
                            "Echec formulaire", JOptionPane.ERROR_MESSAGE);

                }

            }
        });

        //bouton Quitter
        btnQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                fenetre.setVisible(false);
            }
        });

        //settings
        fenetre.setTitle("Formulaire");
        fenetre.setSize(500, 375);
        fenetre.setLocationRelativeTo(null);
        fenetre.setResizable(true);
        fenetre.setVisible(true);
    }

    public void ajouterEmploye() {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.sessionFactory.openSession();
            transaction = session.beginTransaction();
            Employees employe = new Employees();

            employe.setFirstName(txtFirstName.getText().trim());
            employe.setLastName(txtLastName.getText().trim());
            employe.setEmail(txtEmail.getText().trim());
            employe.setHireDate(new Date());// a modifier

            Jobs jobSelectionne = executeHQLQueryJobs("from Jobs j where j.jobId = '"
                    + comboBoxJob.getSelectedItem() + "'");
            employe.setJobs(jobSelectionne);

            session.save(employe);
            txtEmployeeId.setText(employe.getEmployeeId() + "");
            transaction.commit();
            session.close();
            JOptionPane.showMessageDialog(null, "Employe ajouté avec succes", "Modification", JOptionPane.INFORMATION_MESSAGE);

        } catch (HibernateException e) {
            JOptionPane.showMessageDialog(null, "Echec ajout employe", "Echec formulaire", JOptionPane.ERROR_MESSAGE);
            transaction.rollback();
            session.close();

        }
    }

    private void genererComboBoxJobsID() {
        Session session = new HibernateUtil().sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Jobs");
        List listJobs = query.list();
        for (Object object : listJobs) {
            Jobs job = (Jobs) object;
            comboBoxJob.addItem(job.getJobId());
        }
        session.close();
    }

    //execution requete HQL
    private Jobs executeHQLQueryJobs(String query) {

        try {
            Session session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();
            Query q = session.createQuery(query);
            List resultat = q.list();
            Jobs job = (Jobs) resultat.get(0);
            session.getTransaction().commit();
            return job;
        } catch (HibernateException e) {
            JOptionPane.showMessageDialog(null, e, "executeHQLQueryJobs()", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    //ComboBox Locations Listener
    ItemListener itemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            Jobs job = executeHQLQueryJobs("from Jobs j where j.jobId = '" + itemEvent.getItem() + "'");
            lblJobIdDeails.setText(job.getJobTitle());
        }
    };

    //class pour calendar picker
    private static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "dd-MM-yyyy";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }

}

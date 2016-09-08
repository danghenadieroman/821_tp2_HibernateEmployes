package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Administrateur
 */
public class FenSaisirDepartement {

    JFrame fenetre;

    JPanel jpPrincipal;
    JPanel jpNord;
    JPanel jpFormulaire;
    JPanel jpBoutons;

    JLabel lblTitre;

    JLabel lblDepartementId;
    JLabel lblNomDepartement;
    JLabel lblManagerId;
    JLabel lblLocationId;

    JTextField txtDepartementId;
    JTextField txtNomDepartement;
    JTextField txtManagerId;
    JTextField txtLocationId;

    JButton btnEnregistrer;
    JButton btnQuitter;

    public FenSaisirDepartement() {

        prepareGUI();

    }

    private void prepareGUI() {

        fenetre = new JFrame();

        lblTitre = new JLabel("Nouveau departement");
        lblDepartementId = new JLabel("Departement ID: ");
        lblNomDepartement = new JLabel("Nom departement: ");
        lblManagerId = new JLabel("Manager ID: ");
        lblLocationId = new JLabel("Location ID: ");

        txtDepartementId = new JTextField(10);
        txtNomDepartement = new JTextField(10);
        txtManagerId = new JTextField(10);
        txtLocationId = new JTextField(10);

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
        jpFormulaire = new JPanel(new GridLayout(5, 2, 20, 10));

        jpPrincipal.add(jpFormulaire, BorderLayout.CENTER);

//        jpFormulaire.add(new JLabel());
        jpFormulaire.add(lblDepartementId);
        lblDepartementId.setHorizontalAlignment(SwingConstants.RIGHT);
        jpFormulaire.add(txtDepartementId);

        jpFormulaire.add(lblNomDepartement);
        lblNomDepartement.setHorizontalAlignment(SwingConstants.RIGHT);
        jpFormulaire.add(txtNomDepartement);

        jpFormulaire.add(lblManagerId);
        lblManagerId.setHorizontalAlignment(SwingConstants.RIGHT);
        jpFormulaire.add(txtManagerId);

        jpFormulaire.add(lblLocationId);
        lblLocationId.setHorizontalAlignment(SwingConstants.RIGHT);
        jpFormulaire.add(txtLocationId);

        //panel jpBoutons
        jpBoutons = new JPanel();
        jpPrincipal.add(jpBoutons, BorderLayout.SOUTH);
        jpBoutons.add(btnEnregistrer);
        jpBoutons.add(btnQuitter);

        //bouton Quitter
        btnQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                fenetre.setVisible(false);
            }
        });

        //settings
        fenetre.setTitle("Saisir Departement");
        fenetre.setSize(400, 300);
        fenetre.setLocationRelativeTo(null);
        fenetre.setResizable(true);
        fenetre.setVisible(true);
    }

}

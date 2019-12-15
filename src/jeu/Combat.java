package jeu;

import gui.LabyrintheGraphique;

public class Combat {
    private Hero hero;
    private Monstre monstre;
    private boolean combatTermine;
    private Entite gagnant;

    public Combat(Hero hero, Monstre monstre) {
        this.hero = hero;
        this.monstre = monstre;
        combatTermine = false;
        gagnant = null;
    }

    private void phaseJoueur(LabyrintheGraphique labyrintheGraphique) {
        if (hero.getVie() > 0 && !combatTermine) {
            hero.setChoix(labyrintheGraphique.showDialogChoixCombat(hero.getVie(), monstre.getVie()));
            Action action = hero.infligerAction();
            if (action.getTypeAction() == TypeAction.Fuite) {
                combatTermine = true;
            }
            if (monstre.subirAction(action)) {
                combatTermine = true;
                gagnant = hero;
            }
        }
    }

    private void phaseMonstre() {
        if (monstre.getVie() > 0 && !combatTermine) {
            if (hero.subirAction(monstre.infligerAction())) {
                combatTermine = true;
                gagnant = monstre;
            }
        }
    }

    public Entite run(LabyrintheGraphique labyrintheGraphique) {
        while (!combatTermine) {
            phaseJoueur(labyrintheGraphique);
            phaseMonstre();
        }
        return gagnant;
    }

}
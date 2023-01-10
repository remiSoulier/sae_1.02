import java.util.*;
import java.lang.*;
public class  RelationBinaire {
    // attributs
    //DERNIERE MODIF GH 16/12
    private int n;           // n > 0, E = {0,1,2, ..., n-1}
    private boolean[][] matAdj;  // matrice d'adjacence de R
    private int m;           // cardinal de R
    private EE[] tabSucc;    // tableau des ensembles de successeurs
    // constructeurs
    /** pré-requis : nb > 0
     action : construit la relation binaire vide dans l'ensemble {0,1,2, ..., nb-1}
     */
    public RelationBinaire(int nb){
        this.n=nb;
        this.matAdj = new boolean[nb][nb];
        for (int i=0; i<nb; i++) {
            for (int j=0; j<nb; j++) {
                this.matAdj[i][j]=false;
            }
        }
        this.m=0;
        this.tabSucc = new EE[nb];
        for (int i=0; i<this.n; i++) {
            this.tabSucc[i] = new EE(nb);
        }
    }
    //______________________________________________
    /** pré-requis : nb > 0 et 0 <= p <= 1
     action : construit une relation binaire aléatoire dans l'ensemble {0,1,2, ..., nb-1}
     à laquelle chaque couple a la probabilité p d'appartenir.
     En particulier, construit la relation vide si p = 0 et la relation pleine si p = 1.
     Indication : Math.random() retourne un réel de type double aléatoire de l'intervalle [0,1[
     */
    public RelationBinaire(int nb,double p){
        this(nb);
        if (p==1) {
            for (int i=0; i<nb; i++) {
                for (int j=0; j<nb; j++) {
                    this.matAdj[i][j]=true;
                    this.m++;
                    this.tabSucc[i].ajoutElt(j);
                }
            }
        }
        else {
            for (int i=0; i<nb; i++) {
                for (int j=0; j<nb; j++) {
                    double d = Math.random();
                    if (d<=p) {
                        this.matAdj[i][j]=true;
                        this.m++;
                        this.tabSucc[i].ajoutElt(j);
                    }
                    else {
                        this.matAdj[i][j]=false;
                    }
                }
            }
        }
    }
    //______________________________________________
    /** pré-requis : nb > 0
     action : construit la relation binaire dans l'ensemble {0,1,2, ..., nb-1}
     '=' si egal a la valeur vrai et '<=' sinon
     */
    public RelationBinaire(int nb, boolean egal){
        this(nb);
        for (int i=0; i<nb; i++) {
            this.matAdj[i][i]=true;
            this.m++;
            this.tabSucc[i].ajoutElt(i);
        }
        if (!egal) {
            for (int i=0; i<nb; i++) {
                for (int j=0; j<nb; j++) {
                    if (i<j) {
                        this.matAdj[j][i]=true;
                        this.m++;
                        this.tabSucc[j].ajoutElt(i);
                    }
                }
            }
        }
    }
    //______________________________________________
    /** pré-requis : mat est une matrice carrée de dimension > 0
     action : construit une relation binaire dont la matrice d'adjacence
     est une copie de mat
     //MODIF
     */
    public RelationBinaire(boolean[][] mat){
        this(mat.length);
        for (int i=0; i<mat.length; i++) {
            for (int j=0; j<mat.length; j++) {
                this.matAdj[i][j] = mat[i][j];
                if (this.matAdj[i][j]) {
                    this.m++;
                    this.tabSucc[i].ajoutElt(j);
                }
            }
        }
    }
    //______________________________________________
    /** pré-requis : tab.length > 0 et pour tout i, les éléments de tab[i]
     sont compris entre 0 et tab.length-1
     action : construit une relation binaire dont le tableau des ensembles de successeurs
     est une copie de tab
     */
    public RelationBinaire(EE[] tab){
        this(tab.length);
        for (int i=0; i<this.n; i++) {
            this.tabSucc[i]=tab[i];
            this.m+=tab[i].getCardinal();
            for (int j=0; j<this.n; j++) {
                if (this.tabSucc[i].contient(j)) {
                    this.matAdj[i][j]=true;
                }
            }
        }
    }
    //______________________________________________
    /** pré-requis : aucun
     action : construit une copie de r
     */
    public RelationBinaire(RelationBinaire r) {
        this(r.n);
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                this.matAdj[i][j] = r.matAdj[i][j];
                this.m++;
                this.tabSucc[i].ajoutElt(j);
            }
        }
    }
    //______________________________________________
    // méthodes
    /** pré-requis : aucun
     résultat : une chaîne de caractères permettant d'afficher this par sa matrice d'adjacence
     contenant des '0' et des '1' (plus lisibles que des 'V' et des 'F') et sa définition
     en extension (ensemble de couples {(..,..),(..,..), ...})
     */
    public String toString(){
        String result="matrice d'adjacence : \n";
        for ( int i=0;i<this.matAdj.length;i++){
            for (int j=0;j<this.matAdj[i].length;j++){
                if (this.matAdj[i][j]){
                    result = result + "1" +",";
                }
                else {
                    result = result + '0' +",";
                }
            }
            result = result + "\n";
        }
        result += "\ncouples : {";
        for (int i=0;i<this.matAdj.length;i++){
            for (int j=0;j<this.matAdj[i].length;j++){
                if (this.matAdj[i][j]){
                    result += "("+i+","+j+")";
                }
            }
            result += ",";
        }
        result += "}";
        return result;
    }
    //______________________________________________
    // A) Logique et calcul matriciel
    //-------------------------------
    /** pré-requis : m1 et m2 sont des matrices carrées de même dimension et 1 <= numConnecteur <= 5
     résultat : la matrice obtenue en appliquant terme à terme le  connecteur de numéro numConnecteur
     sur m1 si numConnecteur  = 3 (dans ce cas le paramètre m2 n'est pas utilisé),
     et sur m1 et m2 dans cet ordre sinon, sachant que les connecteurs "ou","et","non",
     "implique"et "equivalent" sont numérotés de 1 à 5 dans cet ordre
     */
    public static boolean[][] opBool(boolean[][] m1, boolean[][] m2,int numConnecteur)
    {
        boolean [][] res = new boolean[m1.length][m1.length];
        if (numConnecteur==1){
            for (int i=0;i<m1.length;i++){
                for (int j=0;j<m1.length;j++){
                    res[i][j]=m1[i][j]||m2[i][j];
                }
            }
        }
        if (numConnecteur==2){
            for (int i=0;i<m1.length;i++){
                for (int j=0;j<m1.length;j++){
                    res[i][j]=m1[i][j]&&m2[i][j];
                }
            }
        }
        if (numConnecteur==3){
            for (int i=0;i<m1.length;i++){
                for (int j=0;j<m1.length;j++){
                    res[i][j]= !m1[i][j];
                }
            }
        }
        if (numConnecteur==4){
            for (int i=0;i<m1.length;i++){
                for (int j=0;j<m1.length;j++){
                    if (!m1[i][j]){
                        res[i][j]=true;
                    }
                    else{
                        if(m2[i][j]){ res[i][j]=true; }
                        else { res[i][j]=false;}
                    }
                }
            }
        }
        if (numConnecteur==5){
            for (int i=0;i<m1.length;i++){
                for (int j=0;j<m1.length;j++){
                    res[i][j]=(m1[i][j]==m2[i][j]);
                }
            }
        }
        return res;
    }
    //______________________________________________
    /** pré-requis : m1 et m2 sont des matrices carrées de même dimension
     résultat : le produit matriciel de m1 et m2
     */
    public static boolean[][] produit(boolean[][] m1, boolean[][] m2){
        boolean[][] res = new  boolean[m1.length][m1.length];
        for (int i=0;i<m1.length;i++){
            for (int j=0;j<m1.length;j++){
                res[i][j]=false;
            }
        }
        for (int i=0;i<m1.length;i++){
            for (int j=0;j<m1.length;j++){
                for (int k=0;k<m1.length;k++){

                    res[i][j]= res[i][j] || (m1[i][k] && m2[j][k]);
                }

            }

        }
        return res;
    }
    //______________________________________________
    /** pré-requis : m est une matrice carrée
     résultat : la matrice transposée de m
     */
    public static boolean[][] transposee(boolean[][] m){
        boolean [][] res = new boolean[m.length][m.length];
        for (int i=0;i<m.length;i++){
            for (int j=0;j<m.length;j++){
                res [i][j] = m[j][i];
            }
        }
        return res;
    }
    //______________________________________________
    // B) Théorie des ensembles
    //--------------------------
    /** pré-requis : aucun
     résultat : vrai ssi this est vide
     */
    public boolean estVide(){
        for (int i=0; i<this.n; i++) {
            for (int j=0; j<this.n; j++) {
                if (this.matAdj[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    //______________________________________________
    /** pré-requis : aucun
     résultat : vrai ssi this est pleinee (contient tous les couples d'éléments de E)
     */
    public boolean estPleine(){
        for (int i=0; i<this.n; i++) {
            for (int j=0; j<this.n; j++) {
                if (!this.matAdj[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    //______________________________________________
    /** pré-requis : aucun
     résultat : vrai ssi (x,y) appartient à this
     */
    public boolean appartient(int x, int y){
        if (!this.matAdj[x][y]) {
            return false;
        }
        return true;
    }

    //______________________________________________
    /** pré-requis : 0 <= x < this.n et 0 <= y < this.n
     résultat : ajoute (x,y) à this s'il n'y est pas déjà
     */
    public void ajouteCouple(int x, int y){
        this.tabSucc[x].ajoutElt(y);
        this.matAdj[x][y]=true;
        this.m++;
    }

    //______________________________________________
    /** pré-requis : 0 <= x < this.n et 0 <= y < this.n
     résultat : enlève (x,y) de this s'il y est
     */
    public void enleveCouple(int x, int y){
        this.tabSucc[x].retraitElt(y);
        this.matAdj[x][y]=false;
        this.m--;
    }

    //______________________________________________
    /** pré-requis : aucun
     résultat : une nouvelle relation binaire obtenue à partir de this en ajoutant
     les couples de la forme  (x,x) qui n'y sont pas déjà
     */
    public RelationBinaire avecBoucles(){
        RelationBinaire r = new RelationBinaire(this);
        for (int i=0; i<r.n; i++) {
            if (!r.matAdj[i][i]) {
                r.ajouteCouple(i, i);
            }
        }
        return r;
    }
    //______________________________________________

    /** pré-requis : aucun
     résultat : une nouvelle relation binaire obtenue à partir de this en enlèvant
     les couples de la forme  (x,x) qui y sont
     //DERNIERE MODIF
     */
    public RelationBinaire sansBoucles(){
        RelationBinaire r = new RelationBinaire(this);
        for (int i=0; i<r.n; i++) {
            if (r.matAdj[i][i]) {
                r.enleveCouple(i, i);
            }
        }
        return r;
    }

    //______________________________________________
    /** pré-requis : this.n = r.n
     résultat : l'union de this et r
     */
    public RelationBinaire union(RelationBinaire r){
        return new RelationBinaire(opBool(this.matAdj, r.matAdj, 1));
    }

    //______________________________________________
    /** pré-requis : this.n = r.n
     résultat : l'intersection de this et r
     */
    public RelationBinaire intersection(RelationBinaire r){
        return new RelationBinaire(opBool(this.matAdj, r.matAdj, 2));
    }

    //______________________________________________
    /** pré-requis : aucun
     résultat : la relation complémentaire de this
     */
    public RelationBinaire complementaire(){
        boolean[][] res= new boolean[this.matAdj.length][this.matAdj.length];
		for(int i=0;i<res.length;i++){
			for(int j=0;j<res.length;j++){
				res[i][j]= !this.matAdj[i][j];
			}
		}
		RelationBinaire r = new RelationBinaire(res);
        return r;
    }

    //______________________________________________
    /** pré-requis : this.n = r.n
     résultat : la différence de this et r
     */
    public RelationBinaire difference(RelationBinaire r){
        for (int i=0 ; i<r.n; i++) {
            for (int j=0 ; j<r.n; j++) {
                if (r.matAdj[i][j]) {
                    r.enleveCouple(i, j);
                }
                else {
                    if (this.matAdj[i][j]) {
                        r.ajouteCouple(i, j);
                    }
                    else {
                        r.enleveCouple(i, j);
                    }
                }
            }
        }
        return r;
    }
    //______________________________________________
    /** pré-requis : this.n = r.n
     résultat : vrai ssi this est incluse dans r
     */
    public boolean estIncluse(RelationBinaire r){
        for (int i=0; i<this.n; i++) {
            for (int j=0; j<this.n; j++) {
                if (this.matAdj[i][j]&&!r.matAdj[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    //______________________________________________
    /** pré-requis : this.n = r.n
     résultat : vrai ssi this est égale à r
     */
    public boolean estEgale(RelationBinaire r){
        for (int i=0; i<this.n; i++) {
            for (int j=0; j<this.n; j++) {
                if (this.matAdj[i][j]!=r.matAdj[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    //______________________________________________
    // C) Théorie des graphes orientés
    //---------------------------------
    /** pré-requis : 0 <= x < this.n
     résultat : l'ensemble des successeurs de x dans this, "indépendant"
     (c'est-à-dire dans une autre zône mémoire) de l'attribut this.tabSucc
     */
    public EE succ(int x){
        EE succ = new EE (this.n);
        for (int i=0; i<this.n; i++) {
            if (this.matAdj[x][i]) {
                succ.ajoutElt(i);
            }
        }
        return succ;
    }
    //______________________________________________
    /** pré-requis : 0 <= x < this.n
     résultat : l'ensemble des prédécesseurs de x dans this
     */
    public EE pred(int x){
        EE pred = new EE (this.n);
        for (int i=0; i<this.n; i++) {
            if (this.tabSucc[i].contient(x)) {
                pred.ajoutElt(i);
            }
        }
        return pred;
    }
    //______________________________________________
    // D) Relation binaire
    //---------------------
    /** pré-requis : aucun
     résultat : vrai ssi this est réflexive
     */
    public boolean estReflexive(){
        for (int i=0;i < this.matAdj.length;i++){
            if(!this.matAdj[i][i]){
                return false;
            }
        }
        return true;
    }
    //______________________________________________
    /** pré-requis : aucun
     résultat : vrai ssi this est antiréflexive
     */
    public boolean estAntireflexive(){
        for (int i=0;i < this.matAdj.length;i++){
            if(this.matAdj[i][i]){
                return false;
            }
        }
        return true;
    }
    //______________________________________________
    /** pré-requis : aucun
     résultat : vrai ssi this est symétrique
     */
    public boolean estSymetrique(){
        for (int i =0;i<this.matAdj.length;i++){
            for (int j =0;j<this.matAdj[i].length;j++){
                if (this.matAdj[i][j]!=this.matAdj[j][i]){
                    return false;
                }
            }
        }
        return true;
    }
    //______________________________________________
    /** pré-requis : aucun
     résultat : vrai ssi this est antisymétrique
     */
    public boolean estAntisymetrique(){
        for (int i =0;i<this.matAdj.length;i++){
            for (int j =0;j<this.matAdj[i].length;j++){
                if ((this.matAdj[i][j]==this.matAdj[j][i])&&(i!=j)){
                    return false;
                }
            }
        }
        return true;
    }
    //______________________________________________
    /** pré-requis : aucun
     résultat : vrai ssi this est transitive
     */
    public boolean estTransitive(){
        for (int i=0; i<this.tabSucc.length; i++) {
            for (int j=0; j<this.tabSucc.length; j++) {
                if (this.tabSucc[i].contient(j)) {
                    for (int k=0; k<this.tabSucc.length; k++) {
                        if (this.tabSucc[j].contient(k) && !this.tabSucc[i].contient(k)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    //______________________________________________
    /** pré-requis : aucun
     résultat : vrai ssi this est une relation d'ordre
     */
    public boolean estRelOrdre(){
        if (this.estReflexive()) {
            if (this.estAntisymetrique()) {
                if (this.estTransitive()) {
                    return true;
                }
            }
        }
        return false;
    }
    //______________________________________________
    /** pré-requis : aucun
     résultat : la relation binaire assiciée au diagramme de Hasse de this
     */
    public RelationBinaire hasse(){
        RelationBinaire rb = new RelationBinaire(this);
        for (int i=0; i<this.tabSucc.length; i++) {
            for (int j=0; j<this.tabSucc.length; j++) {
                if (this.tabSucc[i].contient(j)) {
                    for (int k=0; k<this.tabSucc.length; k++) {
                        if (this.tabSucc[j].contient(k) && this.tabSucc[i].contient(k)) {
                            rb.enleveCouple(j, k);
                        }
                    }
                }
            }
        }
        return rb;
    }
    //______________________________________________
    /** pré-requis : aucun
     résultat : la fermeture transitive de this
     */
    public RelationBinaire ferTrans(){
        RelationBinaire rbfermetureTransitive = new RelationBinaire(this);
        while (!this.estTransitive()) {
            for (int i=0; i<rbfermetureTransitive.tabSucc.length; i++) {
                for (int j=0; j<rbfermetureTransitive.tabSucc.length; j++) {
                    if (this.tabSucc[i].contient(j)) {
                        for (int k=0; k<rbfermetureTransitive.tabSucc.length; k++) {
                            if (rbfermetureTransitive.tabSucc[j].contient(k) && !rbfermetureTransitive.tabSucc[i].contient(k)) {
                                rbfermetureTransitive.ajouteCouple(i, k);
                            }
                        }
                    }
                }
            }
        }
        return rbfermetureTransitive;
    }
    //______________________________________________
    /** pré-requis : aucun
     action : affiche this sous 2 formes (matrice et ensemble de couples), puis affiche ses propriétés
     (réflexive, ..., relation d'ordre) et les relations binaires suivantes obtenues à partir de this :
     Hasse, fermeture transitive de Hasse et fermeture transitive de Hasse avec boucles (sous 2 formes aussi)
     */
    public void afficheDivers(){
        this.toString();
        if (this.estReflexive()) {
            System.out.println(" Elle est reflexive.");
        }
        if (this.estAntireflexive()) {
            System.out.println(" Elle est antireflexive.");
        }
        if (this.estSymetrique()) {
            System.out.println(" Elle est symetrique.");
        }
        if (this.estAntisymetrique()) {
            System.out.println(" Elle est antisymetrique.");
        }
        if (this.estTransitive()) {
            System.out.println(" Elle est transitive.");
        }
        if (this.estRelOrdre()) {
            System.out.println(" Elle est donc une relation d'ordre.");
        }
        System.out.println(" \n Hasse : ");
        this.hasse().toString();
        System.out.println(" \n Fermeture transitive de Hasse : ");
        this.hasse().ferTrans().toString();
        System.out.println(" \n Fermeture transitive de Hasse avec boucles : ");
        this.hasse().ferTrans().avecBoucles().toString();
    }
    //______________________________________________
    public static void main(String[] args) {
        int nb;
        double p;
        do {
            Ut.afficher("\nDonner le cardinal de E (>0) : ");
            nb = Ut.saisirEntier();
        }
        while (nb <= 0);
        boolean[][] tamere = new boolean[][] {{false,false,true,true},{true,false,true,true},{false,false,true,true},{false,false,true,true}};
        boolean[][] tasoeur = new boolean[][] {{false,false,true,true},{true,false,true,false},{false,true,true,false},{true,true,true,true}};
        RelationBinaire b = new RelationBinaire(tamere);
        RelationBinaire a = new RelationBinaire(tasoeur);
        System.out.println(b.union(a).toString());
    }
} // fin RelationBinaire

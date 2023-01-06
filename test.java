public class test {
    public static void main(String[] args) {
        boolean[][] m1= {{false,true,true},
                        {true,false,false},
                        {true,false,false},};
        

        boolean[][] m2= {{false,true,false},
                        {true,true,false},
                        {false,true,false},};
	    System.out.println(m2[0][1]);
        System.out.println(m2[1][1]);
        System.out.println(m2[2][1]);
       /* boolean[][] res = new  boolean[m1.length][m1.length];
        for (int i=0;i<m1.length;i++){
            for (int j=0;j<m1.length;j++){
                res[i][j]=false;
            }
        }
        for (int i=0;i<m1.length;i++){
            for (int j=0;j<m1.length;j++){
                for (int k=0;k<m1.length;k++){
                    res[i][j]= res[i][j] || ( m1[i][k] && m2[k][i] );
                    System.out.println(i+" "+k+"  "+k+" "+j+" | "+m1[i][k] +"+"+ m2[k][i] +"="+ res[i][j] + "  ");
                }
                System.out.println("-S"+res[i][j]);
            }
            System.out.println("-------------------------------");
        }



        String result="matrice d'adjacence : \n";
        for ( int i=0;i<res.length;i++){
            for (int j=0;j<res[i].length;j++){
                if (res[i][j]){
                    result = result + "1" +",";
                }
                else {
                    result = result + '0' +",";
                }
            }
            result = result + "\n";
        }
        result += "\ncouples : {";
        for (int i=0;i<res.length;i++){
            for (int j=0;j<res[i].length;j++){
                if (res[i][j]){
                    result += "("+i+","+j+")";
                }
            }
            result += ",";
        }
        result += "}";
        System.out.println(result);
        */
    }
}

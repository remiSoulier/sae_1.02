public class test {
    public static void main(String[] args) {
        boolean[][] m2= {{false,true,true},
                        {true,false,false},
                        {true,false,false},};
        

        boolean[][] m1= {{false,true,false},
                        {true,true,false},
                        {false,true,false},};

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

    }
}

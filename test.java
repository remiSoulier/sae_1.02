public class test {
    public static void main(String[] args) {
        boolean[][] m= {{false,true,false},
                        {true,false,true},
                        {false,true,true},};

        boolean[][] m2= {{true,true,true},
                        {false,true,true},
                        {false,false,false},};

        boolean [][] res = new boolean[m.length][m.length];
        for (int i=0;i<m.length;i++){
            for (int j=0;j<m.length;i++){
                res [i][j] = m[j][i];
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

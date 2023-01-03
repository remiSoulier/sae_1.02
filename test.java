public class test {
    public static void main(String[] args) {
        boolean[][] t= {{false,true,true},
                        {true,true,false},
                        {false,true,true},};
        String result="matrice d'adjacence : \n";
        for ( int i=0;i<t.length;i++){
            for (int j=0;j<t[i].length;j++){
                if (t[i][j]){
                    result = result + "1" +",";
                }
                else {
                    result = result + '0' +",";
                }
            }
            result = result + "\n";
        }
        System.out.println(result);
    }
}

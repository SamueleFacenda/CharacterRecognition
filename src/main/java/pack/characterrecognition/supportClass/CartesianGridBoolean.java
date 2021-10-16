package pack.characterrecognition.supportClass;

public class CartesianGridBoolean {
    boolean[][] grid;
    public CartesianGridBoolean(Blob in){
        grid=new boolean[in.getHeight()][in.getWidth()];
        int minX=in.getLefter(),minY=in.getDowner();
        for(int y=grid.length-1;y>=0;y--){
            for(int x=0;x<grid[x].length;x++){
                if(in.containsSimilar(new Coor((grid.length-y+1)+minY,x+minX)))
                    grid[y][x]=true;
            }
        }
    }
    public boolean get(int x,int y){
        return grid[grid.length-y-1][x];
    }
    @Override
    public String toString(){
        String out="";
        for(int y=grid.length-1;y>=0;y--){
            for(int x=0;x<grid[x].length;x++){
                out+=grid[y][x]?"@":" ";
            }
            out+="\n";
        }
        return out;
    }
}

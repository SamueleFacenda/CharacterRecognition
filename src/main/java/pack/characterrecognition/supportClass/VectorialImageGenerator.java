package pack.characterrecognition.supportClass;

import java.util.Iterator;
import java.util.LinkedList;

public class VectorialImageGenerator extends VectorialImage{
    private double deltaRad,rad360;
    private LinkedList<BlobSegment> blobList;
    private boolean[][] grid;
    public VectorialImageGenerator(Blob in){
        segmentList=new LinkedList<>();
        deltaRad=Math.atan(1.51/2.5);
        blobList=new LinkedList<>();
        rad360=Math.PI*2;
        grid=in.toBooleanGrid();
    }
    private void generateArchs(){

    }
    private void generateBlobSegment(Coor s,Coor e){
        BlobSegment longest=generateSegmentRecursive(new BlobSegment(s),Coor.calcRad(s,e),grid.length/2);
    }
    private BlobSegment generateSegmentRecursive(BlobSegment bs,double radAng,int ttl){
        BlobSegment out=null;
        if(ttl>=0) {
            LinkedList<BlobSegment> currentList = new LinkedList<>();
            BlobSegment blobSegBuffer;
            for (Coor c :
                    getAroundDirection(bs.e, radAng)) {
                blobSegBuffer = bs.getCopy();
                blobSegBuffer.moveEnd(c);
                if (blobSegBuffer.getMaxDist() > 3)
                    generateBlobSegment(bs.e, c);
                else {
                    blobSegBuffer = generateSegmentRecursive(blobSegBuffer,radAng, ttl - 2);
                    if (blobSegBuffer != null)
                        currentList.add(blobSegBuffer);
                }
            }
            if (currentList.size() != 0) {
                Iterator<BlobSegment> iterator = currentList.iterator();
                out = iterator.next();
                while (iterator.hasNext()) {
                    blobSegBuffer = iterator.next();
                    if (blobSegBuffer.getLen() > out.getLen())
                        out = blobSegBuffer;
                }
            }
        }
        return out;
    }
    private Coor[] getAroundDirection(Coor center,double radAng){
        double startNoZone=(radAng+Math.PI-deltaRad)%rad360,endNoZone=(radAng+Math.PI+deltaRad)%rad360;
        int count=0;
        Coor[] out=new Coor[16];
        if(endNoZone>startNoZone)
            endNoZone+=rad360;
        Coor check;
        for (double i = center.x-2; i < center.x+3; i++){
            check=new Coor(i,center.y+2);
            if(checkCoorInDirecton(center,check,startNoZone,endNoZone))
                out[count]=check;
            count++;
            check=new Coor(i,center.y-2);
            if(checkCoorInDirecton(center,check,startNoZone,endNoZone))
                out[count]=check;
            count++;
        }
        for (double i = center.y-1; i < center.y+2; i++){
            check=new Coor(center.x-2,i);
            if(checkCoorInDirecton(center,check,startNoZone,endNoZone))
                out[count]=check;
            count++;
            check=new Coor(center.x+2,i);
            if(checkCoorInDirecton(center,check,startNoZone,endNoZone))
                out[count]=check;
            count++;
        }
        return out;
    }
    private boolean checkCoorInDirecton(Coor center,Coor check,double startNoZone,double endNoZone){
        double radCheck=Coor.calcRad(center,check);
         return check.x>=0 && check.x<grid[0].length && check.y>=0 && grid[(int) check.y][(int) check.x] && check.y<=grid.length&&!isGhegenuber(radCheck,startNoZone,endNoZone);
    }
    private boolean isGhegenuber(double radCheck,double startZone,double endZone){
        boolean isUpStart=radCheck>startZone,isUpEnd=radCheck>endZone;
        if(isUpEnd==isUpStart){
            isUpStart=radCheck>startZone+rad360;
            isUpEnd=radCheck>endZone+rad360;
            return isUpEnd!=isUpStart;
        }else
            return true;
    }
}

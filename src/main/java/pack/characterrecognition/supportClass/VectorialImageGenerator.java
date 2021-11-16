package pack.characterrecognition.supportClass;

import java.util.Iterator;
import java.util.LinkedList;

public class VectorialImageGenerator extends VectorialImage{
    private final double rad360,deltaRad,diagonalHop;
    private boolean[][] grid;
    public VectorialImageGenerator(Blob in){
        segmentList=new LinkedList<>();
        deltaRad=Math.atan(1.51/2.5);
        rad360=Math.PI*2;
        grid=in.toBooleanGrid();
        diagonalHop=Math.sqrt(Math.pow((double)grid.length/2+2, 2)+Math.pow((double)grid[0].length/2+2,2 ));
        int i=0;
        while(!grid[(i/ grid[0].length)][i%grid[0].length]) i++;
        Coor start=new Coor((i/ grid[0].length),i%grid[0].length);
        generateBlobSegmentAtStart(start);
    }
    private void generateArchs(){

    }

    /**
     * launcher del metodo ricorsivo per generare segmenti in tutte le direzioni
     * @param s
     */
    private void generateBlobSegmentAtStart(Coor s){
        Iterator<Segment> iter;
        BlobSegment blobSegBuffer;
        for (Coor c :
                getAroundDirection(s, 0,true)) {
            if(c!=null){
                blobSegBuffer = generateSegmentRecursive(new BlobSegment(s,c),diagonalHop);
                iter=segmentList.iterator();
                while(iter.hasNext() && !Segment.areSimilar(iter.next(), blobSegBuffer,5));
                if(!iter.hasNext())
                    segmentList.add(blobSegBuffer);
            }
        }
    }

    /**
     * metodo ricorsivo per generare segmenti in una direzione
     * @param bs
     * @param ttl
     * @return
     */
    private BlobSegment generateSegmentRecursive(BlobSegment bs,double ttl){
        BlobSegment out=bs;
        if(ttl>=0) {
            double radAng=Coor.calcRad(bs.s,bs.e);
            LinkedList<BlobSegment> currentList = new LinkedList<>();
            BlobSegment blobSegBuffer;
            for (Coor c :
                    getAroundDirection(bs.e, radAng,false)) {
                if(c!=null){
                    blobSegBuffer = bs.getCopy();
                    blobSegBuffer.moveEnd(c);
                    if (blobSegBuffer.getMaxDist() > 3) {
                        blobSegBuffer = generateSegmentRecursive(new BlobSegment(bs.e,c),diagonalHop);
                        Iterator<Segment> iter=segmentList.iterator();
                        while(iter.hasNext() && !Segment.areSimilar(iter.next(), blobSegBuffer,5));
                        if(!iter.hasNext())
                            segmentList.add(blobSegBuffer);
                    }
                    else
                        currentList.add(generateSegmentRecursive(blobSegBuffer, ttl - 1));
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
    private Coor[] getAroundDirection(Coor center,double radAng,boolean isAllOk){
        double startNoZone=(radAng+Math.PI-deltaRad)%rad360,endNoZone=(radAng+Math.PI+deltaRad)%rad360;
        int count=0;
        Coor[] out=new Coor[16];
        if(endNoZone>startNoZone)
            endNoZone+=rad360;
        Coor check;
        for (double i = center.x-2; i < center.x+3; i++){
            check=new Coor(i,center.y+2);
            if(isAllOk || checkCoorInDirecton(center,check,startNoZone,endNoZone))
                out[count]=check;
            count++;
            check=new Coor(i,center.y-2);
            if(isAllOk ||checkCoorInDirecton(center,check,startNoZone,endNoZone))
                out[count]=check;
            count++;
        }
        for (double i = center.y-1; i < center.y+2; i++){
            check=new Coor(center.x-2,i);
            if(isAllOk ||checkCoorInDirecton(center,check,startNoZone,endNoZone))
                out[count]=check;
            count++;
            check=new Coor(center.x+2,i);
            if(isAllOk ||checkCoorInDirecton(center,check,startNoZone,endNoZone))
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

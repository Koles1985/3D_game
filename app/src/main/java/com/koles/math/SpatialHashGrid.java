package com.koles.math;

import com.koles.angin.GameObject;

import java.util.ArrayList;
import java.util.List;

public class SpatialHashGrid {
    List<GameObject>[] dynamicObjects;
    List<GameObject>[] staticObjects;
    private int objectsPerRow;
    private int objectsPerCol;
    private float objectSize;
    private int[] objectIds = new int[4];
    List<GameObject> foundObjects;

    @SuppressWarnings("unchecked")
    public SpatialHashGrid(float worldWidth, float worldHeight, float objectSize){
        this.objectSize = objectSize;
        this.objectsPerRow = (int)Math.ceil(worldWidth / objectSize);
        this.objectsPerCol = (int)Math.ceil(worldHeight / objectSize);
        int numObjects = objectsPerRow * objectsPerCol;
        dynamicObjects = new List[numObjects];
        staticObjects = new List[numObjects];
        for(int i = 0; i < numObjects; i++){
            dynamicObjects[i] = new ArrayList<GameObject>(10);
            staticObjects[i] = new ArrayList<GameObject>(10);
        }
        foundObjects = new ArrayList<GameObject>(10);
    }

    public void insertStaticObject(GameObject obj){
        int[] cellsIds = getObjectIds(obj);
        int i = 0;
        int cellId = - 1;
        while(i <= 3 && (cellId = cellsIds[i++]) != -1){
            staticObjects[cellId].add(obj);
        }
    }

    public void insertDynamicObject(GameObject obj){
        int[] cellsIds = getObjectIds(obj);
        int i = 0;
        int cellId = - 1;
        while(i <= 3 && (cellId = cellsIds[i++]) != -1){
            dynamicObjects[cellId].add(obj);
        }
    }

    public void removeObject(GameObject obj){
        int[] cellsIds = getObjectIds(obj);
        int i = 0;
        int cellId = -1;
        while(i <= 3 &&(cellId = cellsIds[i++]) != -1){
            dynamicObjects[cellId].remove(obj);
            staticObjects[cellId].remove(obj);
        }
    }

    public void clearDynamicObjects(GameObject obj) {
        int len = dynamicObjects.length;
        for (int i = 0; i < len; i++) {
            dynamicObjects[i].clear();
        }
    }

        public int[] getObjectIds(GameObject obj){
            int x1 = (int)Math.floor(obj.bounds.lowerLeft.x / objectSize);
            int y1 = (int)Math.floor(obj.bounds.lowerLeft.y / objectSize);

            int x2 = (int)Math.floor((obj.bounds.lowerLeft.x + obj.bounds.width) / objectSize);
            int y2 = (int)Math.floor((obj.bounds.lowerLeft.y + obj.bounds.height) / objectSize);

            if(x1 == x2 && y1 == y2){
                if(x1 >= 0 && x1 < objectsPerRow && y1 >= 0 && y1 < objectsPerCol)
                    objectIds[0] = x1 + y1 * objectsPerRow;
                else
                    objectIds[0] = -1;
                objectIds[1] = -1;
                objectIds[2] = -1;
                objectIds[3] = -1;
            }else if(x1 == x2){
                int i = 0;
                if(x1 >= 0 && x1 < objectsPerRow){
                    if(y1 >= 0 && y1 < objectsPerCol)
                        objectIds[i++] = x1 + y1 * objectsPerRow;
                    if(y2 >= 0 && y2 < objectsPerCol)
                        objectIds[i++] = x1 + y2 * objectsPerRow;
                }
                while(i <= 3) objectIds[i++] = -1;
            }else if(y1 == y2){
                int i = 0;
                if(y1 >= 0 && y1 < objectsPerCol){
                    if(x1 >= 0 && x1 < objectsPerRow)
                        objectIds[i++] = x1 + y1 * objectsPerRow;
                    if(x2 >= 0 && x2 < objectsPerRow)
                        objectIds[i++] = x2 + y1 * objectsPerRow;
                }
                while(i <= 3) objectIds[i++] = -1;
            }else{
                int i = 0;
                int y1ObjectPerRow = y1 * objectsPerRow;
                int y2ObjectPerRow = y2 * objectsPerRow;
                if(x1 >= 0 && x1 < objectsPerRow && y1 >= 0 && y1 < objectsPerCol)
                    objectIds[i++] = x1 + y1ObjectPerRow;
                if(x2 >= 0 && x2 < objectsPerRow && y1 >= 0 && y1 < objectsPerCol)
                    objectIds[i++] = x2 + y1ObjectPerRow;
                if(x2 >= 0 && x2 <objectsPerRow && y2 >= 0 && y2 < objectsPerCol)
                    objectIds[i++] = x2 + y2ObjectPerRow;
                if(x1 >= 0 && x1 < objectsPerRow && y2 >= 0 && y2 < objectsPerCol)
                    objectIds[i++] = x1 + y2ObjectPerRow;
                while(i <= 3) objectIds[i++] = -1;
            }
            return objectIds;
        }

}

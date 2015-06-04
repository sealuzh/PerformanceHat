/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package CloudWave;

public enum CloudWaveJNI {
  instance;
  
  public static final String CLOUDWAVE_LIB = "cloudwavejni";
  
  CloudWaveJNI() {System.loadLibrary(CLOUDWAVE_LIB);}
  public static CloudWaveJNI getInstance(){return instance;}
  
  public void init() throws CloudWaveException{ 
    int r = initJNI(); 
    if (r<0) {
      System.err.println("initJNI returned " + r);
      throw new CloudWaveException();
    }
  }
  
  public void free(){ 
    freeJNI(); 
  }
      
  protected IEventHandler eventHandler;
  public IEventHandler getEventHandler() {
    return eventHandler;
  }
  
  public void setEventHandler(IEventHandler eh) {
    synchronized(this){ eventHandler = eh;} 
  }
  
  public void doEvent(String event){
    synchronized(this) {
      if (eventHandler!=null)  
        eventHandler.doEvent(event); 
    }
  }

  protected synchronized static void callback(String event){
    instance.doEvent(event);
  }
  
  //#: Init/Free 
  
  public native int initJNI();
    protected native int freeJNI();
    
    //:#
    
    //#: Log 
    
    protected native int initLog();
    protected native int freeLog();

    protected native int setLogId(String id);
    protected native String getLogId();
    protected native int recordLog(int level, String message);
    protected native int recordLogL(int level, String message, long id);
    
    //:#
        
    //#: Metric 
    
    protected native int initMetric();
    protected native int freeMetric();
  
    protected native int recordMetricL(int source, String name, String mdata, String munit, int type, long value);
    protected native int recordMetricD(int source, String name, String mdata, String munit, int type, double value);
    protected native int recordMetricS(int source, String name, String mdata, String munit, int type, String value);
    protected native int recordEventL(int source, String name, String mdata, String munit, int type, long value);
    protected native int recordEventD(int source, String name, String mdata, String munit, int type, double value);
    protected native int recordEventS(int source, String name, String mdata, String munit, int type, String value);

    //:#
    
    //#: Events 
    
    protected native int initEvent();
    protected native int freeEvent();

    protected native int postEvent(String event_json);
    protected native long subscribe(String event_id);
    protected native int unsubscribe(long id);
    
    //:#
}

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

public enum CloudWave {
  instance;
  
  CloudWave() { }
  
  public static CloudWave getInstance(){return instance;}
  
  public void init() throws CloudWaveException{ CloudWaveJNI.instance.init(); }
  public void free() { CloudWaveJNI.instance.free(); }
  
  //#: Log Interface
    
    public void setLogId(String id) throws CloudWaveException {
      int r = CloudWaveJNI.instance.setLogId(id);
      if(r<0)throw new CloudWaveException();
    }
      
    public String getLogId() {
      return CloudWaveJNI.instance.getLogId();
    }
      
    public void record(LogLevel level, String message) throws CloudWaveException {
      int r = CloudWaveJNI.instance.recordLog(level.ordinal(), message);
      if(r<0)throw new CloudWaveException();
    }
      
    public void record(LogLevel level, String message, long id) throws CloudWaveException {
      int r = CloudWaveJNI.instance.recordLogL(level.ordinal(), message, id);
      if(r<0)throw new CloudWaveException();
    }
      
    //:#
    
  //#: Metric Interface
    
  public void recordMetric(MetricEventSource source, String name, String mdata, String munit, MetricEventType type, long value) throws CloudWaveException{
    int r = CloudWaveJNI.instance.recordMetricL(source.ordinal(), name, mdata, munit,type.ordinal(),value);
    if(r<0)throw new CloudWaveException();
    }
    public void recordMetric(MetricEventSource source, String name, String mdata, String munit, MetricEventType type, double value) throws CloudWaveException{
      int r = CloudWaveJNI.instance.recordMetricD(source.ordinal(), name, mdata, munit,type.ordinal(),value);
      if(r<0)throw new CloudWaveException();
    }
    public void recordMetric(MetricEventSource source, String name, String mdata, String munit, MetricEventType type, String value) throws CloudWaveException{
      int r = CloudWaveJNI.instance.recordMetricS(source.ordinal(), name, mdata, munit,type.ordinal(),value);
      if(r<0)throw new CloudWaveException();
    }
    public void recordEvent(MetricEventSource source, String name, String mdata, String munit, MetricEventType type, long value) throws CloudWaveException{
      int r = CloudWaveJNI.instance.recordEventL(source.ordinal(), name, mdata, munit,type.ordinal(),value);
      if(r<0)throw new CloudWaveException();
    }
    public void recordEvent(MetricEventSource source, String name, String mdata, String munit, MetricEventType type, double value) throws CloudWaveException{
      int r = CloudWaveJNI.instance.recordEventD(source.ordinal(), name, mdata, munit,type.ordinal(),value);
      if(r<0)throw new CloudWaveException();
    }
    public void recordEvent(MetricEventSource source, String name, String mdata, String munit, MetricEventType type, String value) throws CloudWaveException{
      int r = CloudWaveJNI.instance.recordEventS(source.ordinal(), name, mdata, munit,type.ordinal(),value);
      if(r<0)throw new CloudWaveException();
    }
      
    //:#
    
  //#: Events Interface
    
    public void postEvent(String event_json) throws CloudWaveException{
      int r =CloudWaveJNI.instance.postEvent(event_json);
      if(r<0)throw new CloudWaveException();
    }
      
    public long subscribe(String event_id){
      return CloudWaveJNI.instance.subscribe(event_id);
    }
    public  void unsubscribe(long id) throws CloudWaveException{
      int r = CloudWaveJNI.instance.unsubscribe(id);
      if(r<0)throw new CloudWaveException();
    }
      
    public IEventHandler getEventHandler() { return CloudWaveJNI.instance.getEventHandler(); }
    public void setEventHandler( IEventHandler handler) { CloudWaveJNI.instance.setEventHandler(handler); }
        
  //:#
}


package ElevatorSimulationPackage.Common;
/*Kyle Cardiel
 * SE450 - Elevator Project
 * Timer Class - This object is used to support a log of activity with easily formatted time.
 * 
 */


public class Timer {

  private long startTime = 0;
  private long endTime   = 0;
  private long totalTime;
  private long hours;
  private long minutes;
  private long seconds;
  private long milliseconds;
  private static Timer instance;
  
  
  //singleton constructor
  private Timer(){ }
  
  public static synchronized Timer getInstance() {
		if (instance == null){
			instance = new Timer();
			}
		return instance;
	}
  
  //Access
  public long getStartTime() {
	  return startTime;}
  
  public long getEndTime() {
	  return endTime;}
  
  public long getHours(){
	  return hours;}
  
  public long getMinutes(){
	  return minutes;}
  
  public long getSeconds(){
	  return seconds;}
  
  public long getMilliseconds(){
	  return milliseconds;}  
  
  public long getTotalTime() {
	  totalTime = getEndTime() - getStartTime();
	  setHours(totalTime/1440000);
	  setMinutes(totalTime/60000);
	  setSeconds(totalTime/1000%60);
	  setMilliseconds(totalTime%1000);  
	  return totalTime;
  }

  //Modifiers
  private void setHours(long hoursIn){
	  hours = hoursIn;}
  
  private void setMinutes(long minutesIn){
	  minutes = minutesIn;}
  
  private void setSeconds(long secondsIn){
	  seconds = secondsIn;}
  
  private void setMilliseconds(long millisecondsIn){
	  milliseconds = millisecondsIn;}
    
  //Operations
  public void start(){startTime = System.currentTimeMillis();}
  
  public void end() {endTime = System.currentTimeMillis();}
  
  public String toStringTotalTime(){
	  getTotalTime();
	  StringBuilder sb = new StringBuilder();
		sb.append(String.format("%02d:", getHours()));
		sb.append(String.format("%02d:", getMinutes()));
		sb.append(String.format("%02d.", getSeconds()));
		sb.append(String.format("%03d", getMilliseconds()));
		return sb.toString();
  }
  
  
}

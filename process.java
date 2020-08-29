public class process extends cores {
     int pid=0;
     int atime=0;
     int btime=0;
     int priority=0;
     int waitingtime=0;
     int completiontime=0;
     int turnaroundtime=0;
     int status=0;
     process()
     {
         
     }
    process(int pid1,int atime1, int btime1, int priority1)
    {
        pid=pid1;
        atime=atime1;
        btime=btime1;
        priority=priority1;
    }
    process(int pid1,int atime1, int btime1, int priority1, int status1, int ct, int wt, int tat)
    {
        pid=pid1;
        atime=atime1;
        btime=btime1;
        priority=priority1;
        waitingtime=wt;
        completiontime=ct;
        turnaroundtime=tat;
        status=status1;
    }
    int getpid(int p)
    {
        return pid;
    }
    int getatime()
    {
        return atime;
    }
      int getbtime()
    {
        return btime;
    }  
      int getpriority()
    {
        return priority;
    }
      void setpriority(int priority1)
      {
          priority=priority1;
      }
       void setpid(int pid1)
      {
          pid=pid1;
      }
        void setbtime(int btime1)
      {
          btime=btime1;
      }
         void setatime(int atime1)
      {
          atime=atime1;
      }
     public String toString()
     {
         return atime+"  "+ "  "+btime+"  "+priority;
     }
     
}

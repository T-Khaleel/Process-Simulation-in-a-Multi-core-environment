import java.util.*;
import java.io.*;


public class Procsim {
    private static ArrayList<Integer> array1=new ArrayList<Integer>();
    private static ArrayList<Integer> array2 = new ArrayList<Integer>();
    static int processnum=0;
    static int corenum;
    static cores[] c1;
    static int x=0;

    ArrayList<Integer> fileread(String s) throws FileNotFoundException {
    File f = new File(s);
    ArrayList<Integer> a1 = new ArrayList();
    Scanner scan = new Scanner(f);
    while (scan.hasNext())
    {
       array2.add(scan.nextInt());
    }

    for(int i=0;i<array2.size();i++)
    {
        if(i>1)
      {
        int j=array2.get(i);
        a1.add(j);
      }
    }
    return a1;
  }
   
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
     
       Procsim readprocess =new Procsim();
       array1=readprocess.fileread(args[0]);      
       processnum=array2.get(1);
       corenum=array2.get(0);
       
       process[] array3=new process[processnum];
       
       process p[]=new process[processnum];
           int y=3;
                     for(int i=0;i<processnum;i++) // creating process objects
                    {
                        if(i==0)
                         {
                        p[i]=new process(array1.get(0), array1.get(1), array1.get(2), array1.get(y));
                         }  
                        else
                         {
                        p[i]=new process(array1.get(y+1), array1.get(y+2), array1.get(y+3), array1.get(y+4));
                        y=y+4;
                         }
                    }
                     
        Comparator<process> atimecompare=new Comparator<process>()
        {
                public int compare(process o1, process o2)
                {
                    return o1.getatime()-o2.getatime();
                }
        };
 
       
        ArrayList<process> atimelist=new ArrayList<process>();                
            for(int i=0;i<processnum;i++)
                atimelist.add(p[i]);              
       
        Collections.sort(atimelist, atimecompare);
   
         for(int i=0;i<processnum;i++)
            array3[i]=atimelist.get(i);    
   
    int a=Integer.parseInt(args[1]);
    switch(a)
    {
        case 1:          
         coresmani(array3,processnum,corenum,1);              
            break;
           
        case 2:
            coresmani(array3,processnum,corenum,2);            
            break;
           
        case 3:
            coresmani(array3,processnum,corenum,3);
                break;
    }
}

static void sjf(ArrayList<process> p, int n, cores c1, process array5[])
{
    int[] wt=new int[n];
    wt[0]=0;
    int c=1,flag=0, flag1=0;
    int tempct=0; // temporary count of completion time
     
    Comparator<process> btimecompare=new Comparator<process>()
    {
        public int compare(process o1, process o2)
        {
            if((o1.getbtime()-o2.getbtime())==0)
                return o1.getatime()-o2.getatime();
                    else
                return o1.getbtime()-o2.getbtime();
        }
     };
     
    ArrayList<process> p1=new ArrayList<process>(); // arraylist excluding first process
     
    for(int i=1;i<n;i++)
    {
        p1.add(p.get(i));
    }
       
    for(int i=0;i<p.get(0).atime;i++) // if first process doesn't arrive at time 0
    {
        if(i==0)
            wt[0]=c;
         else
            wt[0]=wt[0]+c;
    }
   
    //calculating first process's CT,AT,BT of each core
    tempct= p.get(0).getbtime()+wt[0];
    array5[x].completiontime=tempct;
    array5[x].turnaroundtime=array5[x].completiontime-c1.atime.get(0);
    array5[x].waitingtime=array5[x].turnaroundtime-c1.btime.get(0);
               
    
   
    x=x+1; // incrementing to next process
         
    Collections.sort(p1,btimecompare);  //sorting
    int j;
   
    if( p1.size()!=0 && ((tempct)<p1.get(0).atime))// if cpu is idle between first and second process
    {
        for(j=tempct;j<p1.get(0).atime;j++)
        {  
            wt[1]=j-tempct+1;
        }
        wt[1]=wt[1]+tempct;          
        flag=1;
    }
//             System.out.println("wt[1]:"+wt[1]);
    wt[0]=array5[0].completiontime;

   
    for(int i=0;i<p1.size();i++)
    {
        Collections.sort(p1,btimecompare); //sort on burst time              
        tempct= p1.get(i).getbtime()+wt[i];
        array5[x].completiontime=tempct;
        array5[x].turnaroundtime=array5[x].completiontime-p1.get(i).atime;
        array5[x].waitingtime=array5[x].turnaroundtime-p1.get(i).btime;    
           
        if(flag==1) // if cpu is idle between first and second process
        {
                
            tempct= p1.get(i).getbtime()+wt[1];
            array5[x].completiontime=tempct;
            array5[x].turnaroundtime=array5[x].completiontime-p1.get(i).atime;
            array5[x].waitingtime=array5[x].turnaroundtime-p1.get(i).btime;    
            flag=0;
       }
   

        j=0;
        if(((i+1)<n-1)&& !((i+1)>=n-1) &&(tempct<p1.get(i+1).atime))// if cpu is idle between processes
        {
            for(j=tempct;j<p1.get(i+1).atime;j++)
            {
                wt[i+1]=j-tempct+1;
            }
       
            wt[i+1]=wt[i+1]+tempct;          
        }
        else if((i+1)<n-1)
            wt[i+1]=tempct;
                                 
        x=x+1;    
   }

}
static void coresmani(process p[], int processnum, int corenum,int f)
{    
     int context=0;      
     int wait=0;
     int completiontime=0;
     int k=corenum;
     int i=0;
     float turnaround=0;
     process[] array4=new process[processnum];
   
     for(i=0;i<processnum;i++)
        array4[i]=p[i];
   
    cores[] c1=new cores[corenum];
        for(i=0;i<corenum;i++) //creating cores
            c1[i]=new cores();
        i=0;
    while(k<processnum) // adding processes to the cores
    {
        int j=0;          
          for(j=0;j<corenum;j++)
            {
                if(k<processnum)
                {
                c1[j].getprocess(p[i].pid,p[i].atime,p[i].btime,p[i].priority,i);
                i=i+1;
                k=i;
                }
            }
    }  
   
    if(f==1)
    {
        for(i=0;i<corenum;i++)
        {
           fcfs(c1[i].p, c1[i].p.size(), c1[i],array4);      
        }

        for( i=0;i<processnum;i++)
        {
            wait=wait+array4[i].waitingtime;
            turnaround=turnaround+array4[i].turnaroundtime;
            completiontime=array4[i].completiontime;
        }

	int max=0;
	
	for(i=0;i<processnum;i++)
	{
			
	if(max < array4[i].completiontime)
		max=array4[i].completiontime;
	}
         System.out.println("Context: "+ (processnum-corenum));
         System.out.println("Total Time:" +max);
         System.out.println("Waitingg Time: "+wait);
         System.out.println("Turn around average: "+turnaround/processnum);
    }
   
   
    if(f==2)
     {
        for(i=0;i<corenum;i++)
        {
            sjf(c1[i].p, c1[i].p.size(),c1[i],array4);      
        }    
               
        for( i=0;i<processnum;i++)
        {
            wait=wait+array4[i].waitingtime;
            turnaround=turnaround+array4[i].turnaroundtime;
            completiontime=array4[i].completiontime;
        }

	int max=0;
	for(i=0;i<processnum;i++)
	{
			
		if(max < array4[i].completiontime)
			max=array4[i].completiontime;
	}
         System.out.println("Context: "+ (processnum-corenum));    
         System.out.println("Total Time:" +max);
         System.out.println("Waitingg Time: "+wait);
         System.out.println("Turn around average: "+turnaround/processnum);
    }
   
   if(f==3)
    {
      for(i=0;i<corenum;i++)
        {
            priority(c1[i].p, c1[i].p.size(),c1[i],array4);      
        }
     
        for( i=0;i<processnum;i++)
        {
            wait=wait+array4[i].waitingtime;
            turnaround=turnaround+array4[i].turnaroundtime;
            completiontime=array4[i].completiontime;
        }
     
        for(i=0;i<corenum;i++)
         context=c1[i].context+context;
       
	int max=0;
	
	for(i=0;i<processnum;i++)
	{
			
	if(max < array4[i].completiontime)
		max=array4[i].completiontime;
	}

        System.out.println("Completion Time:" +max);
        System.out.println("Waitingg Time: "+wait);
        System.out.println("Turn around average: "+turnaround/processnum);
        System.out.println("Context Switching: "+context);

    }
}

   static void fcfs(ArrayList<process> p1, int n, cores c1,process array5[])
    {
       
        int ct[]=new int[n]; // starting time for each process
        ct[0]=0;
        int tempct=0;
        int c=1;        
       
        for(int i=0;i<p1.get(0).atime;i++) // if arrival time for process 1 is not 0
        {
            if(i==0)
                ct[0]=c;
            else
                ct[0]=ct[0]+c; //calculating start time for process 1
        }
       
         for(int i=0;i<n;i++)
        {      
            int j=0;
           
            tempct= p1.get(i).getbtime()+ct[i];
           
            array5[x].completiontime=tempct;
            array5[x].turnaroundtime=array5[x].completiontime-c1.atime.get(i);
            array5[x].waitingtime=array5[x].turnaroundtime-c1.btime.get(i);            

            if(((i+1)<n)&& !((i+1)>=n) &&(tempct<p1.get(i+1).atime))// if cpu is idle between processes
               {
                     for(j=tempct;j<p1.get(i+1).atime;j++)
                     {  
                     ct[i+1]=j-tempct+1;
                     }
                     ct[i+1]=ct[i+1]+tempct;
               }
               else
                   if((i+1)<n)
                        ct[i+1]=tempct;
            x=x+1; // incrementing to next process
        }
     
     
   }

   


static void priority(ArrayList<process> p1, int n, cores c1, process array5[])
{
    int j, min,clock;
    ArrayList<Integer> p_pid=new ArrayList<Integer>();
    int[] pt=new int[n+2];
    int[] tempbt=new int[n+2];
    int remain=n;
   
    Comparator<process> prioritycompare=new Comparator<process>()
    {
                public int compare(process o1, process o2)
                {
                    return o1.getatime()-o2.getatime();
                }
    };
    Collections.sort(p1,prioritycompare);
   
    for(int i=0;i<n;i++)
    {
       pt[i]=p1.get(i).priority;
       tempbt[i]=p1.get(i).btime; // decrement burst time
    }
   
    pt[n+1]=(p1.get(n-1).priority) +10; // max priority

    for(clock=0;remain!=0;)
    {
      min=n+1;
      for(j=0;j<n;j++)
      {
        if( tempbt[j]>0 && p1.get(j).priority<pt[min] && p1.get(j).atime<=clock)
        {
             min=j;
        }
      }
      tempbt[min]=tempbt[min]-1; // decrementing burst time
      if(min!=(n+1))
          p_pid.add(p1.get(min).pid);
     
      if(tempbt[min]==0)  // if burst time is 0
      {
        remain=remain-1;
       array5[x].completiontime=clock+1;
       array5[x].turnaroundtime=array5[x].completiontime-c1.atime.get(min);
       array5[x].waitingtime=array5[x].turnaroundtime-c1.btime.get(min);  
       x=x+1;
      }
     clock=clock+1;
    }
    int pid_nxt=p_pid.get(1);
                  for(int i=0;i<p_pid.size();i++)//calculation of context
                  {
                      if(p_pid.get(i)!=pid_nxt)
                      {
                          c1.context=c1.context+1;
                          pid_nxt=p_pid.get(i);
                      }                    
                  }

}

}

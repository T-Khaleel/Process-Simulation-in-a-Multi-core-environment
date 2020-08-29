import java.util.ArrayList;


public class cores {
   ArrayList<process> p=new ArrayList<process>();
   int j=0;
   int waitingtime=0;
   int turnaroundtime=0;
   int completiontime=0;
   int context=0;
   ArrayList<Integer> btime=new ArrayList<Integer>();
    ArrayList<Integer> atime=new ArrayList<Integer>();
 
    void getprocess(int id, int at, int bt, int priority,int i)
    {
        btime.add(bt);
        atime.add(at);
        p.add(j,new process(id, at,bt,priority));
        j=j+1;
    }
    void display(ArrayList<process> p)
    {
        for(int i=0;i<p.size();i++)
        System.out.println(p.get(i).pid);
    }
   
}

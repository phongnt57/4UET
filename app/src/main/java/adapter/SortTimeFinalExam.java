package adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by phong on 6/2/2015.
 */
public class SortTimeFinalExam {
    List<FinalExamInfo> list;
    public SortTimeFinalExam(List<FinalExamInfo> exams)
    {
        this.list = exams;
    }
    public List<FinalExamInfo> getSort()
    {
        //List<FinalExamInfo> result = new ArrayList<FinalExamInfo>();
        for(int i =0;i< list.size()-1;i++)
            for(int j=i;j<list.size();j++)
            {
               if(compareTime(list.get(i),list.get(j))>0)
               {
                   Collections.swap(list, i,  j);

               }

            }
        return list;
    }
    public int compareTime(FinalExamInfo a, FinalExamInfo b)
    {
        String[] datea = a.getNgay_thi().split("/");
        String[] dateb = b.getNgay_thi().split("/");
        int daya = Integer.parseInt(datea[0]);
        int montha = Integer.parseInt(datea[1]);

        int dayb = Integer.parseInt(dateb[0]);
        int monthb = Integer.parseInt(dateb[1]);

        String ahour[] = a.getGio_thi().split(":");
        String bhour[] = b.getGio_thi().split(":");
        int houra = Integer.parseInt(ahour[0]);

        int hourb = Integer.parseInt(bhour[0]);
        if(montha >monthb)
        {
            return 1;
        }
        else if(montha <monthb)
        {
            return  -1;
        }
        else if(daya >dayb)
        {
            return 1;
        }
        else if(daya<dayb)
        {
            return -1;
        }
        else if(houra> hourb)
        {
            return 1;
        }
        else
        {
            return -1;
        }


    }
}

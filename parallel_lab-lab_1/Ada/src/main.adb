with Ada.Text_IO;

procedure Main is

   can_stop : boolean := false;
   pragma Atomic(can_stop);

   task type break_thread is
      entry Start(time_break : Integer);
      end break_thread;
   task type main_thread is
         entry Start(idd : Integer; step : Long_Long_Integer);
       end main_thread;





   task body break_thread is
      time_br : Duration;
   begin
      accept Start(time_break : in Integer) do
         time_br := Duration (time_break);
            end Start;

      delay time_br;
      can_stop := true;
   end break_thread;

   task body main_thread is
      	  sum : Long_Long_Integer := 0;
         cnt_steps : Long_Long_Integer := 0;
         id : Integer;
         step : Long_Long_Integer;
      begin
         accept Start(idd : Integer;step : Long_Long_Integer) do
            main_thread.id := idd;
            main_thread.step := step;
            end Start;
      loop
         sum := sum + step;
         cnt_steps := cnt_steps + 1;
         exit when can_stop;
      end loop;
      delay 1.0;

      Ada.Text_IO.Put_Line("id: " & id'Img & " sum: "& sum'Img & " Steps Num: " & cnt_steps'Img & " Step: " & step'Img);
   end main_thread;


   threads_cnt : Integer:= 2;
   step : Long_Long_Integer := 3;
   time_break : Integer := 1;
   breaker : break_thread;


begin
   declare

   A : Array(1..threads_cnt) of main_thread;
   begin
      breaker.Start(time_break);
      For I in A'Range Loop
         A(I).Start(I,step);
         end loop;
      end;
end Main;

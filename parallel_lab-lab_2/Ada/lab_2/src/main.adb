with Ada.Text_IO; use Ada.Text_IO;
procedure Main is

   dim : constant Integer := 100000;
   thread_num : constant Integer := 5;
   arr : array(1..dim) of integer;

   procedure Init_Arr is
   begin
      for i in 1..dim loop
         arr(i) := i;
      end loop;

      arr(15) := -256;
   end Init_Arr;

   function part_min(start_index, finish_index : in integer) return long_long_integer is
      min : long_long_integer := 9000000000;
   begin

      for i in start_index..finish_index-1 loop
         if long_long_integer(arr(i)) <= min then
            min := long_long_integer(arr(i));
         end if;
      end loop;
      return min;
   end part_min;

function min_index(min_num : in Long_Long_Integer) return Integer is
      min_ind : Integer := -1;
   begin

      for i in 1..dim-1 loop
         if long_long_integer(arr(i)) = min_num then
            min_ind := i;
         end if;
      end loop;
      return min_ind;
   end min_index;
   task type starter_thread is
      entry start(start_index, finish_index : in Integer);
   end starter_thread;

   protected part_manager is
      procedure set_part_min(min : in Long_Long_Integer);
      entry get_min(min : out Long_Long_Integer);
   private
      tasks_count : Integer := 0;
      min1 : Long_Long_Integer := 9000000000;
   end part_manager;

   protected body part_manager is
      procedure set_part_min(min : in Long_Long_Integer) is
      begin
         min1 := Long_Long_Integer'Min(min1, min);
         tasks_count := tasks_count + 1;
      end set_part_min;

      entry get_min(min : out Long_Long_Integer) when tasks_count = thread_num is
      begin
         min := min1;
      end get_min;

   end part_manager;

   task body starter_thread is
      min : Long_Long_Integer := 9000000000;
      start_index, finish_index : Integer;
   begin
      accept start(start_index, finish_index : in Integer) do
         starter_thread.start_index := start_index;
         starter_thread.finish_index := finish_index;
      end start;
      min := part_min(start_index  => start_index,
                      finish_index => finish_index);
      part_manager.set_part_min(min);
   end starter_thread;

   function parallel_min return Long_Long_Integer is
      min : long_long_integer := 9000000000;
      thread : array(1..thread_num) of starter_thread;
   begin

      for i in 1..thread_num loop
         thread(i).start(1 + (i - 1) * dim / thread_num, 1 + i * dim / thread_num);
      end loop;

      part_manager.get_min(min);
      return min;
   end parallel_min;


begin
   Init_Arr;
declare
      min_num : Long_Long_Integer := parallel_min;
begin
   Put_Line("Min num in array: " & min_num'Img);
   Put_Line("Min index: " & min_index(min_num)'Img);
end;
end Main;

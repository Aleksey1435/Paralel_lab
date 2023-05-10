with Ada.Text_IO, GNAT.Semaphores;
use Ada.Text_IO, GNAT.Semaphores;

with Ada.Containers.Indefinite_Doubly_Linked_Lists;
use Ada.Containers;

procedure Main is
   package String_Lists is new Indefinite_Doubly_Linked_Lists (String);
   use String_Lists;

   Item_Numbers : Integer := 10;
   Storage_Size : Integer := 3;
   Producer_count : Integer := 4;
   Consumer_count : Integer := 4;

   Storage : List;
   Access_Storage : Counting_Semaphore (1, Default_Ceiling);
   Full_Storage   : Counting_Semaphore (Storage_Size, Default_Ceiling);
   Empty_Storage  : Counting_Semaphore (0, Default_Ceiling);

   task type Producer is
      entry Start (Id,firstInd,finishInd : in Integer);
   end Producer;

   task type Consumer is
      entry Start (Id,itemNum : in Integer);
   end Consumer;

   task body Producer is
      Id : Integer;
      firstInd  : Integer;
      finishInd: Integer;
   begin
      accept Start (Id,firstInd,finishInd : in Integer) do
         Producer.Id := Id;
         Producer.firstInd := firstInd;
         Producer.finishInd := finishInd;
      end Start;
         for i in firstInd..finishInd loop

         Full_Storage.Seize;
         Access_Storage.Seize;

         declare
               item : String := i'Img;
         begin
             Storage.Append ("item " & item);
             Put_Line ("Added item " & item & "  Id: " & Id'Img);
            end;


            Access_Storage.Release;
            Empty_Storage.Release;
            delay 1.5;
         end loop;

      end Producer;

   task body Consumer is
      Id : Integer;
      itemNum : Integer;
      begin
      accept Start (Id,itemNum : in Integer) do
         Consumer.Id := Id;
         Consumer.itemNum := itemNum;
      end Start;

      for i in 1..itemNum loop
         Empty_Storage.Seize;
         Access_Storage.Seize;

         declare
            item : String := First_Element (Storage);
         begin
              Put_Line ("Took " & item & "  Id: "& id'Img);
         end;
            Storage.Delete_First;

            Access_Storage.Release;
            Full_Storage.Release;

            delay 2.0;
         end loop;

      end Consumer;

   procedure Starter(num_prod : in Integer; num_cons : in Integer) is
      cnt_prod : Integer := num_prod;
      cnt_cons : Integer := num_cons;
      Arr_prod  : Array(1..cnt_prod) of Producer;
      Arr_cons  : Array(1..cnt_cons) of Consumer;
   begin
      for i in 1..cnt_prod loop
         Arr_prod(i).Start(i,(i - 1) * Item_Numbers / Producer_count,i * Item_Numbers / Producer_count);
      end loop;

      for i in 1..cnt_cons loop
         Arr_cons(i).Start(i,i * Item_Numbers / Consumer_count - (i - 1) * Item_Numbers / Consumer_count + 1);
      end loop;
   end Starter;

begin
   Starter(Producer_count,Consumer_count);
end Main;

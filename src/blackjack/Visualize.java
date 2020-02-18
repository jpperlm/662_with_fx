package blackjack;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

// Interface for all visualizations and for interactions with user
public interface Visualize {

   Integer defaultLength = 80;

   default void printSpacer() {
      System.out.println(this.dashes( "-"));
   }

   default void printSpacer(String s) {
      System.out.println(this.dashes( s));
   }

   default void printTitle(String t) {
      System.out.println(this.title("-", t));
   }

   default void printAnnouncement(String t) {
      System.out.println(this.promptTitle("*", t));
   }

   default  void printStatus(String t) {
      this.printSpacer("*");
      this.printAnnouncement(t);
      this.printSpacer("*");
   }

   default void printBasic(String t) {
      System.out.println(this.basic(t));
   }

   default void printPrompt(String t) {
      System.out.println(this.promptTitle("?", t));
   }

   default void printHorizontalOptions(ArrayList<?> options) {
      this.printHorizontalOptions(options, true);
   }

   default void printVerticalOptions(ArrayList<?> options) {
      this.printVerticalOptions(options, true);
   }


   default void printVerticalOptions(@NotNull ArrayList<?> options, Boolean marker) {
      for (int i=0; i < options.size(); ++i) {
         StringBuilder sb = new StringBuilder();
         sb.append("|");

         Object opt = options.get(i);
         String item = "";
//       Downcasting example
         if (opt instanceof Seat) {
            item = ((Seat)opt).getPlayer();
         }else if (opt== null) {
            item = "";
         } else {
            item = opt.toString();
         }

         if (marker) {
            sb.append(" " + (i+1) + ". " + item);
         } else {
            sb.append("   " + item);
         }

         Integer should_end_at = this.defaultLength;
         while (sb.length() < should_end_at) {
            sb.append(' ');
         }
         sb.append('|');
         System.out.println(sb.toString());
      }
   }

   default void printHorizontalOptions(@NotNull ArrayList<?> options, Boolean marker) {
      StringBuilder sb = new StringBuilder();
      sb.append("|");
      Integer maxSectionLength = (this.defaultLength / options.size()) - 3;
      for (int i=0; i < options.size(); ++i) {
         Object opt = options.get(i);
         String item = "";
//       Downcasting example
         if (opt instanceof Seat) {
            item = ((Seat)opt).getPlayer();
         }else if (opt== null) {
            item = "";
         } else {
            item = opt.toString();
         }
         if (item.length() > maxSectionLength ){
            item = item.substring(0, maxSectionLength);
         }
         if (marker) {
            sb.append(" " + (i+1) + ". " + item);
         } else {
            sb.append("   " + item);
         }
         Integer should_end_at = (i+1) * (maxSectionLength + 3);
         while (sb.length() < should_end_at) {
            sb.append(' ');
         }
         if (i+1 == options.size()) {
            while (sb.length() < this.defaultLength) {
               sb.append(' ');
            }
         }
         sb.append('|');
      }
      System.out.println(sb.toString());
   }

   default void waitInput() {
      Scanner input = new Scanner(System.in);
      System.out.print("Press enter to continue...");
      input.nextLine();
   }

   default Integer waitResponse(ArrayList<?> options) {
      return this.waitResponse(options, "");
   }
   default Integer waitResponse(@NotNull ArrayList<?> options, String exit) {
      Integer i = 1;
      for (Object opt: options) {
        this.printBasic(i + ") " +  opt);
         ++i;
      }
      Integer maxInput = options.size();
      if (exit != "") {
         maxInput++;
         this.printBasic( i + ") " +  exit);
      }
      this.printSpacer();
      Scanner input = new Scanner(System.in);
      int number = 0;
      while (number == 0){
         try {
            System.out.print("Enter a number to select: ");
            number = input.nextInt();
            if (number > maxInput || number < 0) {
               System.out.println("Nice try... ");
               number = 0;
            }
         } catch (InputMismatchException ex) {
            System.out.println("That wasn't a number...");
            input.nextLine();
            continue;
         }
      }
      return number - 1;
   }

   @NotNull
   private String dashes(String item) {
      StringBuilder sb = new StringBuilder();
      sb.append("|");
      while (sb.length() < this.defaultLength) {
         sb.append(item);
      }
      sb.append("|");
      return sb.toString();
   }

   @NotNull
   private String basic(String title) {
      StringBuilder sb = new StringBuilder();
      sb.append("| ");
      sb.append(title);
      while (sb.length() < this.defaultLength) {
         sb.append(' ');
      }
      sb.append("|");
      return sb.toString();
   }

   @NotNull
   private String title(String item, String title) {
      StringBuilder sb = new StringBuilder();
      sb.append("|" + item + item + item + ' ');
      sb.append(title);
      while (sb.length() < this.defaultLength) {
         sb.append(' ');
      }
      sb.append("|");
      return sb.toString();
   }

   @NotNull
   private String promptTitle(String item, @NotNull String title) {
      StringBuilder sb = new StringBuilder();
      sb.append("| " + item + item + item);
      Integer spaceRemaining = this.defaultLength - 4 - sb.length();
      Integer titleLength = title.length();
      Integer startSpace = (spaceRemaining - titleLength) / 2 + sb.length();
      while (sb.length() < this.defaultLength - 4) {
         if (sb.length() == startSpace) {
            sb.append(title);
         }else {
            sb.append(' ');
         }
      }
      sb.append(item + item + item + " |");
      return sb.toString();
   }

}

/*Name : Jay Jagtap
3154037
Problem Statement: To achieve File transfer using TCP/IP Protocol
*/

/*
	Server Side
	Please pass port no as command line argument
*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h> 
#include <sys/socket.h>
#include <netinet/in.h>
#include <ctype.h>


void error(const char *msg)
{
    perror(msg);
    exit(1);
}

int main(int argc, char *argv[])
{
     int sockfd, portno;
     char buffer[512];
     struct sockaddr_in serv_addr, cli_addr;
     int n;
     if (argc < 2) {
         fprintf(stderr,"ERROR, no port provided\n");
         exit(1);
     }
     sockfd = socket(AF_INET, SOCK_DGRAM, 0);
     if (sockfd < 0) 
        error("ERROR opening socket");
     memset(&serv_addr, 0, sizeof(serv_addr));
     memset(&cli_addr, 0, sizeof(cli_addr));
     portno = atoi(argv[1]);
     serv_addr.sin_family = AF_INET;
     serv_addr.sin_addr.s_addr = INADDR_ANY;
     serv_addr.sin_port = htons(portno);
     if (bind(sockfd, (struct sockaddr *) &serv_addr, sizeof(serv_addr)) < 0) 
         error("ERROR on binding"); 
   
          FILE *fp;
         int ch = 0;
            fp = fopen("glad_receive.txt","a");            
            int words;
            int cli_len;
		recvfrom(sockfd, &words, sizeof(words), 0, (struct sockaddr *)&cli_addr, &cli_len);
            //printf("Passed integer is : %d\n" , words);      //Ignore , Line for Testing
          while(ch != words)
       	   {
        	 recvfrom(sockfd, buffer, 1024, 0, (struct sockaddr *)&cli_addr, &cli_len);
	   	 fprintf(fp , " %s" , buffer);   
		 //printf(" %s %d "  , buffer , ch); //Line for Testing , Ignore
		 ch++;
	   }
     	printf("The file was received successfully\n");
	   printf("The new file created is gladreceive.txt");
	  fclose(fp);
     close(sockfd);
     return 0; 
}

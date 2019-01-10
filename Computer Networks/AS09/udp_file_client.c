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
        
    FILE *f;
    
    int words = 0;
    char c;
     f=fopen("glad.txt","r");
    while((c=getc(f))!=EOF)			//Counting No of words in the file
	{	
		fscanf(f , "%s" , buffer);
		if(isspace(c)||c=='\t')
		words++;	
	}	
	//printf("Words = %d \n"  , words);	//Ignore
       
      
	sendto(sockfd, &words, sizeof(words), 0, (struct sockaddr *)&serv_addr, sizeof(serv_addr));
     	rewind(f);
      
            /*      fseek(f, 0L, SEEK_END);    	// tells size of the file. Not rquired for the functionality in code.
	int sz = ftell(f);				//Just written for curiosity.
	printf("Size is %d \n" , sz);
          rewind(f);  
            */
       
    char ch ;
       while(ch != EOF)
      {
		
		fscanf(f , "%s" , buffer);
		//printf("%s\n" , buffer);	//Ignore
		sendto(sockfd, buffer, sizeof(buffer), 0, (struct sockaddr *)&serv_addr, sizeof(serv_addr));
		ch = fgetc(f);
      }
	printf("The file was sent successfully");
    
    close(sockfd);
    return 0;
}

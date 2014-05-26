#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct chr
{
	char * chr_name;
	char * seq;
};

char * fasta_concat(char * s1, char * s2)
{
	char * con;
	con = (char *)malloc(strlen(s1) +strlen(s2)+1);
	int i=0;
	for (i=0; i<strlen(s1); i++)
		con[i]=s1[i];
	for (int j=0; j<strlen(s2); j++, i++)
		con[i]=s2[j];
	con[i]='\0';
	return con;
}

char * read_fasta(void)
{
	char * file_contents;
	long input_file_size;
	FILE *input_file = fopen("/Users/thomas/Desktop/w303.fasta","rb");
	fseek(input_file,0,SEEK_END);
	input_file_size = ftell(input_file);
	rewind(input_file);
	file_contents = malloc(input_file_size * (sizeof(char)));
	fread(file_contents,sizeof(char),input_file_size,input_file);
	fclose(input_file);
	return file_contents;
}


int main()
{
	char * w303_file = read_fasta();
	printf("%s\n",w303_file);
	
}
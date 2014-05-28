#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "fasta.c"
#include "gff.c"

char * read_file(char * filename)
{
	char * file_contents;
	long input_file_size;
	FILE *input_file = fopen(filename,"rb");
	fseek(input_file,0,SEEK_END);
	input_file_size = ftell(input_file);
	rewind(input_file);
	file_contents = malloc(input_file_size * (sizeof(char)));
	fread(file_contents,sizeof(char),input_file_size,input_file);
	fclose(input_file);
	return file_contents;
}

unsigned long get_lines(char * filename)
{
	FILE *fp = fopen(filename,"r");
	int c;
	unsigned long line_count = 0;

	/* count the newline characters */
	while ((c=fgetc(fp)) != EOF)
	{
		if (c == '\n')
			line_count++;
	}
	fclose(fp);
	return line_count;
}

void n(void)
{
	printf("\n");
}


/*
char * help_get_gene(char * s)
{
	char * r = malloc((strlen(s)-1) * sizeof(char));
	for (int i=0;i<strlen(r);i++)
	{
		r[i]=s[i+1];
	}
	
	return r;
}

void get_gene(char * s)
{
	struct chr gene[16];
	
	char * pch;
	pch = strtok(s,"\n");
	int c = -1;
	while(pch !=NULL)
	{
		if(pch[0]=='>')
		{
			printf("%s\n",pch);
			c++;
			gene[c].chr_name=help_get_gene(pch);
			pch = strtok(NULL,"\n");
			gene[c].seq=" ";
		}
		
		
		char * seq = gene[c].seq;
		gene[c].seq = malloc((strlen(seq) + strlen(pch)) * sizeof(char));
		strcpy(gene[c].seq,seq);
		strncat(gene[c].seq,pch,strlen(pch));
				
		pch = strtok(NULL,"\n");
	}
	printf("%s\n",gene[0].chr_name);

}
*/



int main()
{
	struct chr * test_fasta;
	test_fasta = read_fasta("w303.fasta"); //works
	write_fasta("w303_write.fasta",test_fasta); //works
	
	
	struct gff test_gff[get_lines("W303_RM.gff")];
	read_gff("W303_RM.gff",test_gff); //works
	stest_gff("W303_RM_write.gff",test_gff,sizeof(test_gff));
	
	/*
	for(int i=0;i<16665;i++)
	{
		printf("%s %s %s %i %i %s %s %s %s\n",
			test_gff[i].chr,
			test_gff[i].data1,
			test_gff[i].feature,
			test_gff[i].start,
			test_gff[i].end,
			test_gff[i].data2,
			test_gff[i].data3,
			test_gff[i].data4,
			test_gff[i].the_rest);
	}*/
}
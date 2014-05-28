#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "fasta.cpp"
#include "gff.cpp"
#include "vcf.cpp"

char * read_file(char * filename)
{
	char * file_contents;
	long input_file_size;
	FILE *input_file = fopen(filename,"rb");
	fseek(input_file,0,SEEK_END);
	input_file_size = ftell(input_file);
	rewind(input_file);
	file_contents = (char*) malloc(input_file_size * (sizeof(char)));
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

int main()
{	
	struct chr * test_fasta;
	test_fasta = read_fasta("w303.fasta"); //works
	write_fasta("w303_write.fasta",test_fasta); //works
	
	
	struct gff test_gff[get_lines("W303_RM.gff")];
	read_gff("W303_RM.gff",test_gff); //works
	write_gff("W303_RM_write.gff",test_gff,sizeof(test_gff));
	
	struct vcf test_vcf[get_lines("example_vcf.vcf")];
	read_vcf("example_vcf.vcf",test_vcf);
}

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct gff
{
	char * chr;
	char * data1;
	char * feature;
	int start;
	int end;
	char * data2;
	char * data3;
	char * data4;
	char * the_rest;
};

char * get_gff(char * filename)
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


void read_gff(char * filename, struct gff * gff_lines)
{
	char * s = get_gff(filename);
	char * newline_split;
	char * tab_split;
	char *saveptr1, *saveptr2;
	char *temp[9];		
	
	newline_split = strtok_r(s,"\n",&saveptr1);
	for(int j=0;newline_split !=NULL;j++)
	{
		tab_split = strtok_r(newline_split,"	",&saveptr2);
		for(int i=0;tab_split !=NULL;i++)
		{
			temp[i]=tab_split;
			tab_split = strtok_r(NULL,"	",&saveptr2);
		}

		gff_lines[j].chr=temp[0];
		gff_lines[j].data1=temp[1];
		gff_lines[j].feature=temp[2];
		gff_lines[j].start=atoi(temp[3]);
		gff_lines[j].end=atoi(temp[4]);
		gff_lines[j].data2=temp[5];
		gff_lines[j].data3=temp[6];
		gff_lines[j].data4=temp[7];
		gff_lines[j].the_rest=temp[8];

		newline_split = strtok_r(NULL,"\n",&saveptr1);
	}
}

void stest_gff(char * filename, struct gff * test_gff, unsigned long size)
{
	FILE *f = fopen(filename, "w");
	if (f == NULL)
	{
	    printf("Error opening file!\n");
	    exit(1);
	}
	
	int n = size/(sizeof(struct gff));
	for(int i=0;i<n;i++)
	{
		fprintf(f,"%s	%s	%s	%i	%i	%s	%s	%s	%s\n",
			test_gff[i].chr,
			test_gff[i].data1,
			test_gff[i].feature,
			test_gff[i].start,
			test_gff[i].end,
			test_gff[i].data2,
			test_gff[i].data3,
			test_gff[i].data4,
			test_gff[i].the_rest);
	}

}